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
  deleteDoc,
  doc,
  endAt,
  endBefore,
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
} from 'firebase/firestore';

import type {
  AddCollectionSnapshotListenerCallback,
  AddCollectionSnapshotListenerEvent,
  AddCollectionSnapshotListenerOptions,
  AddDocumentOptions,
  AddDocumentResult,
  AddDocumentSnapshotListenerCallback,
  AddDocumentSnapshotListenerEvent,
  AddDocumentSnapshotListenerOptions,
  DocumentData,
  FirebaseFirestorePlugin,
  GetCollectionGroupOptions,
  GetCollectionGroupResult,
  GetCollectionOptions,
  GetCollectionResult,
  GetDocumentOptions,
  GetDocumentResult,
  QueryCompositeFilterConstraint,
  QueryConstraint,
  QueryFieldFilterConstraint,
  QueryFilterConstraint,
  QueryNonFilterConstraint,
  RemoveSnapshotListenerOptions,
  SetDocumentOptions,
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

  public async getCollection<T extends DocumentData>(
    options: GetCollectionOptions,
  ): Promise<GetCollectionResult<T>> {
    const collectionQuery = await this.buildCollectionQuery(options);
    const collectionSnapshot = await getDocs(collectionQuery);
    return {
      snapshots: collectionSnapshot.docs.map(documentSnapshot => ({
        id: documentSnapshot.id,
        path: documentSnapshot.ref.path,
        data: documentSnapshot.data() as T,
      })),
    };
  }

  public async getCollectionGroup<T extends DocumentData>(
    options: GetCollectionGroupOptions,
  ): Promise<GetCollectionGroupResult<T>> {
    const collectionQuery = await this.buildCollectionQuery(options);
    const collectionSnapshot = await getDocs(collectionQuery);
    return {
      snapshots: collectionSnapshot.docs.map(documentSnapshot => ({
        id: documentSnapshot.id,
        path: documentSnapshot.ref.path,
        data: documentSnapshot.data() as T,
      })),
    };
  }

  public async clearPersistence(): Promise<void> {
    const firestore = getFirestore();
    await clearIndexedDbPersistence(firestore);
  }

  public async enableNetwork(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async disableNetwork(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
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
      snapshot => {
        const data = snapshot.data();
        const event: AddDocumentSnapshotListenerEvent<T> = {
          snapshot: {
            id: snapshot.id,
            path: snapshot.ref.path,
            data: (data === undefined ? null : data) as T | null,
          },
        };
        callback(event);
      },
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
    const collectionQuery = await this.buildCollectionQuery(options);
    const unsubscribe = onSnapshot(collectionQuery, snapshot => {
      const event: AddCollectionSnapshotListenerEvent<T> = {
        snapshots: snapshot.docs.map(documentSnapshot => ({
          id: documentSnapshot.id,
          path: documentSnapshot.ref.path,
          data: documentSnapshot.data() as T,
        })),
      };
      callback(event);
    });
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
  }

  private async buildCollectionQuery(
    options:
      | GetCollectionOptions
      | GetCollectionGroupOptions
      | AddCollectionSnapshotListenerOptions,
  ): Promise<Query<DocumentData, DocumentData>> {
    const firestore = getFirestore();
    let collectionQuery: Query;
    if (options.compositeFilter) {
      const compositeFilter =
        await this.buildFirebaseQueryCompositeFilterConstraint(
          options.compositeFilter,
        );
      const queryConstraints =
        await this.buildFirebaseQueryNonFilterConstraints(
          options.queryConstraints || [],
        );
      collectionQuery = query(
        collection(firestore, options.reference),
        compositeFilter,
        ...queryConstraints,
      );
    } else {
      const queryConstraints = await this.buildFirebaseQueryConstraints(
        options.queryConstraints || [],
      );
      collectionQuery = query(
        collection(firestore, options.reference),
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
      const firebaseQueryConstraint = await this.buildFirebaseQueryConstraint(
        queryConstraint,
      );
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
