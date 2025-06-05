import { WebPlugin } from '@capacitor/core';
import type {
  QueryCompositeFilterConstraint as FirebaseQueryCompositeFilterConstraint,
  QueryConstraint as FirebaseQueryConstraint,
  QueryFieldFilterConstraint as FirebaseQueryFieldFilterConstraint,
  QueryFilterConstraint as FirebaseQueryFilterConstraint,
  QueryNonFilterConstraint as FirebaseQueryNonFilterConstraint,
  Query,
  Unsubscribe,
} from 'firebase/firestore';
import {
  addDoc,
  and,
  clearIndexedDbPersistence,
  collection,
  collectionGroup,
  connectFirestoreEmulator,
  deleteDoc,
  disableNetwork,
  doc,
  enableNetwork,
  endAt,
  endBefore,
  getCountFromServer,
  getDoc,
  getDocs,
  getFirestore,
  limit,
  limitToLast,
  onSnapshot,
  or,
  orderBy,
  query,
  setDoc,
  startAfter,
  startAt,
  updateDoc,
  where,
  writeBatch,
} from 'firebase/firestore';

import type {
  AddCollectionGroupSnapshotListenerCallback,
  AddCollectionGroupSnapshotListenerOptions,
  AddCollectionSnapshotListenerCallback,
  AddCollectionSnapshotListenerCallbackEvent,
  AddCollectionSnapshotListenerOptions,
  AddDocumentOptions,
  AddDocumentResult,
  AddDocumentSnapshotListenerCallback,
  AddDocumentSnapshotListenerCallbackEvent,
  AddDocumentSnapshotListenerOptions,
  DocumentData,
  FirebaseFirestorePlugin,
  GetCollectionGroupOptions,
  GetCollectionGroupResult,
  GetCollectionOptions,
  GetCollectionResult,
  GetCountFromServerOptions,
  GetCountFromServerResult,
  GetDocumentOptions,
  GetDocumentResult,
  QueryCompositeFilterConstraint,
  QueryConstraint,
  QueryFieldFilterConstraint,
  QueryFilterConstraint,
  QueryNonFilterConstraint,
  RemoveSnapshotListenerOptions,
  SetDocumentOptions,
  UseEmulatorOptions,
  WriteBatchOptions,
} from './definitions';

export class FirebaseFirestoreWeb
  extends WebPlugin
  implements FirebaseFirestorePlugin
{
  private readonly unsubscribesMap: Map<string, Unsubscribe> = new Map();

  public async addDocument(
    options: AddDocumentOptions,
  ): Promise<AddDocumentResult> {
    const firestore = getFirestore();
    const { reference, data } = options;
    const documentReference = await addDoc<DocumentData, DocumentData>(
      collection(firestore, reference),
      data,
    );
    return {
      reference: {
        id: documentReference.id,
        path: documentReference.path,
      },
    };
  }

  public async setDocument(options: SetDocumentOptions): Promise<void> {
    const firestore = getFirestore();
    const { reference, data, merge } = options;
    await setDoc<DocumentData, DocumentData>(doc(firestore, reference), data, {
      merge,
    });
  }

  public async getDocument<T extends DocumentData>(
    options: GetDocumentOptions,
  ): Promise<GetDocumentResult<T>> {
    const firestore = getFirestore();
    const { reference } = options;
    const documentSnapshot = await getDoc(doc(firestore, reference));
    const documentSnapshotData = documentSnapshot.data();
    return {
      snapshot: {
        id: documentSnapshot.id,
        path: documentSnapshot.ref.path,
        data: (documentSnapshotData === undefined
          ? null
          : documentSnapshotData) as T | null,
        metadata: {
          hasPendingWrites: documentSnapshot.metadata.hasPendingWrites,
          fromCache: documentSnapshot.metadata.fromCache,
        },
      },
    };
  }

  public async updateDocument(options: SetDocumentOptions): Promise<void> {
    const firestore = getFirestore();
    const { reference, data } = options;
    await updateDoc<DocumentData, DocumentData>(
      doc(firestore, reference),
      data,
    );
  }

  public async deleteDocument(options: SetDocumentOptions): Promise<void> {
    const firestore = getFirestore();
    const { reference } = options;
    await deleteDoc(doc(firestore, reference));
  }

  public async writeBatch(options: WriteBatchOptions): Promise<void> {
    const firestore = getFirestore();
    const { operations } = options;
    const batch = writeBatch(firestore);
    for (const operation of operations) {
      const { type, reference, data, options } = operation;
      const documentReference = doc(firestore, reference);
      switch (type) {
        case 'set':
          batch.set(documentReference, data, options ?? {});
          break;
        case 'update':
          batch.update(documentReference, data ?? {});
          break;
        case 'delete':
          batch.delete(documentReference);
          break;
      }
    }
    await batch.commit();
  }

  public async getCollection<T extends DocumentData>(
    options: GetCollectionOptions,
  ): Promise<GetCollectionResult<T>> {
    const collectionQuery = await this.buildCollectionQuery(
      options,
      'collection',
    );
    const collectionSnapshot = await getDocs(collectionQuery);
    return {
      snapshots: collectionSnapshot.docs.map(documentSnapshot => ({
        id: documentSnapshot.id,
        path: documentSnapshot.ref.path,
        data: documentSnapshot.data() as T,
        metadata: {
          hasPendingWrites: documentSnapshot.metadata.hasPendingWrites,
          fromCache: documentSnapshot.metadata.fromCache,
        },
      })),
    };
  }

  public async getCollectionGroup<T extends DocumentData>(
    options: GetCollectionGroupOptions,
  ): Promise<GetCollectionGroupResult<T>> {
    const collectionQuery = await this.buildCollectionQuery(
      options,
      'collectionGroup',
    );
    const collectionSnapshot = await getDocs(collectionQuery);
    return {
      snapshots: collectionSnapshot.docs.map(documentSnapshot => ({
        id: documentSnapshot.id,
        path: documentSnapshot.ref.path,
        data: documentSnapshot.data() as T,
        metadata: {
          hasPendingWrites: documentSnapshot.metadata.hasPendingWrites,
          fromCache: documentSnapshot.metadata.fromCache,
        },
      })),
    };
  }

  public async getCountFromServer(
    options: GetCountFromServerOptions,
  ): Promise<GetCountFromServerResult> {
    const firestore = getFirestore();
    const { reference } = options;
    const coll = collection(firestore, reference);
    const snapshot = await getCountFromServer(coll);
    return { count: snapshot.data().count };
  }

  public async clearPersistence(): Promise<void> {
    const firestore = getFirestore();
    await clearIndexedDbPersistence(firestore);
  }

  public async enableNetwork(): Promise<void> {
    const firestore = getFirestore();
    await enableNetwork(firestore);
  }

  public async disableNetwork(): Promise<void> {
    const firestore = getFirestore();
    await disableNetwork(firestore);
  }

  public async useEmulator(options: UseEmulatorOptions): Promise<void> {
    const firestore = getFirestore();
    const port = options.port || 8080;
    connectFirestoreEmulator(firestore, options.host, port);
  }

  public async addDocumentSnapshotListener<
    T extends DocumentData = DocumentData,
  >(
    options: AddDocumentSnapshotListenerOptions,
    callback: AddDocumentSnapshotListenerCallback<T>,
  ): Promise<string> {
    const firestore = getFirestore();
    const unsubscribe = onSnapshot(
      doc(firestore, options.reference),
      {
        includeMetadataChanges: options.includeMetadataChanges,
        source: options.source,
      },
      snapshot => {
        const data = snapshot.data();
        const event: AddDocumentSnapshotListenerCallbackEvent<T> = {
          snapshot: {
            id: snapshot.id,
            path: snapshot.ref.path,
            data: (data === undefined ? null : data) as T | null,
            metadata: {
              hasPendingWrites: snapshot.metadata.hasPendingWrites,
              fromCache: snapshot.metadata.fromCache,
            },
          },
        };
        callback(event, undefined);
      },
      error => callback(null, error),
    );
    const id = Date.now().toString();
    this.unsubscribesMap.set(id, unsubscribe);
    return id;
  }

  public async addCollectionSnapshotListener<
    T extends DocumentData = DocumentData,
  >(
    options: AddCollectionSnapshotListenerOptions,
    callback: AddCollectionSnapshotListenerCallback<T>,
  ): Promise<string> {
    const collectionQuery = await this.buildCollectionQuery(
      options,
      'collection',
    );
    const unsubscribe = onSnapshot(
      collectionQuery,
      {
        includeMetadataChanges: options.includeMetadataChanges,
        source: options.source,
      },
      snapshot => {
        const event: AddCollectionSnapshotListenerCallbackEvent<T> = {
          snapshots: snapshot.docs.map(documentSnapshot => ({
            id: documentSnapshot.id,
            path: documentSnapshot.ref.path,
            data: documentSnapshot.data() as T,
            metadata: {
              hasPendingWrites: documentSnapshot.metadata.hasPendingWrites,
              fromCache: documentSnapshot.metadata.fromCache,
            },
          })),
        };
        callback(event, undefined);
      },
      error => callback(null, error),
    );
    const id = Date.now().toString();
    this.unsubscribesMap.set(id, unsubscribe);
    return id;
  }

  public async addCollectionGroupSnapshotListener<
    T extends DocumentData = DocumentData,
  >(
    options: AddCollectionGroupSnapshotListenerOptions,
    callback: AddCollectionGroupSnapshotListenerCallback<T>,
  ): Promise<string> {
    const collectionQuery = await this.buildCollectionQuery(
      options,
      'collectionGroup',
    );
    const unsubscribe = onSnapshot(
      collectionQuery,
      {
        includeMetadataChanges: options.includeMetadataChanges,
        source: options.source,
      },
      snapshot => {
        const event: AddCollectionSnapshotListenerCallbackEvent<T> = {
          snapshots: snapshot.docs.map(documentSnapshot => ({
            id: documentSnapshot.id,
            path: documentSnapshot.ref.path,
            data: documentSnapshot.data() as T,
            metadata: {
              hasPendingWrites: documentSnapshot.metadata.hasPendingWrites,
              fromCache: documentSnapshot.metadata.fromCache,
            },
          })),
        };
        callback(event, undefined);
      },
      error => callback(null, error),
    );
    const id = Date.now().toString();
    this.unsubscribesMap.set(id, unsubscribe);
    return id;
  }

  public async removeSnapshotListener(
    options: RemoveSnapshotListenerOptions,
  ): Promise<void> {
    const unsubscribe = this.unsubscribesMap.get(options.callbackId);

    if (!unsubscribe) {
      return;
    }

    unsubscribe();
    this.unsubscribesMap.delete(options.callbackId);
  }

  public async removeAllListeners(): Promise<void> {
    this.unsubscribesMap.forEach(unsubscribe => unsubscribe());
    this.unsubscribesMap.clear();
    await super.removeAllListeners();
  }

  private async buildCollectionQuery(
    options:
      | GetCollectionOptions
      | GetCollectionGroupOptions
      | AddCollectionSnapshotListenerOptions,
    type: 'collection' | 'collectionGroup',
  ): Promise<Query<DocumentData, DocumentData>> {
    const firestore = getFirestore();
    let collectionQuery: Query;
    if (options.compositeFilter) {
      const compositeFilter = this.buildFirebaseQueryCompositeFilterConstraint(
        options.compositeFilter,
      );
      const queryConstraints =
        await this.buildFirebaseQueryNonFilterConstraints(
          options.queryConstraints || [],
        );
      collectionQuery = query(
        type === 'collection'
          ? collection(firestore, options.reference)
          : collectionGroup(firestore, options.reference),
        compositeFilter,
        ...queryConstraints,
      );
    } else {
      const queryConstraints = await this.buildFirebaseQueryConstraints(
        options.queryConstraints || [],
      );
      collectionQuery = query(
        type === 'collection'
          ? collection(firestore, options.reference)
          : collectionGroup(firestore, options.reference),
        ...queryConstraints,
      );
    }
    return collectionQuery;
  }

  private buildFirebaseQueryCompositeFilterConstraint(
    compositeFilter: QueryCompositeFilterConstraint,
  ): FirebaseQueryCompositeFilterConstraint {
    const queryConstraints = this.buildFirebaseQueryFilterConstraints(
      compositeFilter.queryConstraints,
    );
    if (compositeFilter.type === 'and') {
      return and(...queryConstraints);
    } else {
      return or(...queryConstraints);
    }
  }

  private buildFirebaseQueryFilterConstraints(
    queryfilterConstraints: QueryFilterConstraint[],
  ): FirebaseQueryFilterConstraint[] {
    const firebaseQueryFilterConstraints: FirebaseQueryFilterConstraint[] = [];
    for (const queryfilterConstraint of queryfilterConstraints) {
      const firebaseQueryFilterConstraint =
        this.buildFirebaseQueryFilterConstraint(queryfilterConstraint);
      firebaseQueryFilterConstraints.push(firebaseQueryFilterConstraint);
    }
    return firebaseQueryFilterConstraints;
  }

  private buildFirebaseQueryFilterConstraint(
    queryFilterConstraints: QueryFilterConstraint,
  ): FirebaseQueryFilterConstraint {
    if (queryFilterConstraints.type === 'where') {
      return this.buildFirebaseQueryFieldFilterConstraint(
        queryFilterConstraints,
      );
    } else {
      return this.buildFirebaseQueryCompositeFilterConstraint(
        queryFilterConstraints,
      );
    }
  }

  private buildFirebaseQueryFieldFilterConstraint(
    queryfilterConstraints: QueryFieldFilterConstraint,
  ): FirebaseQueryFieldFilterConstraint {
    return where(
      queryfilterConstraints.fieldPath,
      queryfilterConstraints.opStr,
      queryfilterConstraints.value,
    );
  }

  private async buildFirebaseQueryNonFilterConstraints(
    queryConstraints: QueryNonFilterConstraint[],
  ): Promise<FirebaseQueryNonFilterConstraint[]> {
    const firebaseQueryNonFilterConstraints: FirebaseQueryNonFilterConstraint[] =
      [];
    for (const queryConstraint of queryConstraints) {
      const firebaseQueryNonFilterConstraint =
        await this.buildFirebaseQueryNonFilterConstraint(queryConstraint);
      firebaseQueryNonFilterConstraints.push(firebaseQueryNonFilterConstraint);
    }
    return firebaseQueryNonFilterConstraints;
  }

  private async buildFirebaseQueryNonFilterConstraint(
    queryConstraints: QueryNonFilterConstraint,
  ): Promise<FirebaseQueryNonFilterConstraint> {
    switch (queryConstraints.type) {
      case 'orderBy':
        return orderBy(
          queryConstraints.fieldPath,
          queryConstraints.directionStr,
        );
      case 'limit':
        return limit(queryConstraints.limit);
      case 'limitToLast':
        return limitToLast(queryConstraints.limit);
      case 'startAt':
      case 'startAfter':
      case 'endAt':
      case 'endBefore': {
        const firestore = getFirestore();
        const documentSnapshot = await getDoc(
          doc(firestore, queryConstraints.reference),
        );
        switch (queryConstraints.type) {
          case 'startAt':
            return startAt(documentSnapshot);
          case 'startAfter':
            return startAfter(documentSnapshot);
          case 'endAt':
            return endAt(documentSnapshot);
          case 'endBefore':
            return endBefore(documentSnapshot);
        }
      }
    }
  }

  private async buildFirebaseQueryConstraints(
    queryConstraints: QueryConstraint[],
  ): Promise<FirebaseQueryConstraint[]> {
    const firebaseQueryConstraints: FirebaseQueryConstraint[] = [];
    for (const queryConstraint of queryConstraints) {
      const firebaseQueryConstraint =
        await this.buildFirebaseQueryConstraint(queryConstraint);
      firebaseQueryConstraints.push(firebaseQueryConstraint);
    }
    return firebaseQueryConstraints;
  }

  private async buildFirebaseQueryConstraint(
    queryConstraint: QueryConstraint,
  ): Promise<FirebaseQueryConstraint> {
    if (queryConstraint.type === 'where') {
      return this.buildFirebaseQueryFieldFilterConstraint(queryConstraint);
    } else {
      return await this.buildFirebaseQueryNonFilterConstraint(queryConstraint);
    }
  }
}
