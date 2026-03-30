import { WebPlugin } from '@capacitor/core';
import { getApp } from 'firebase/app';
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
  GeoPoint as FirebaseGeoPoint,
  Timestamp as FirebaseTimestamp,
  addDoc,
  and,
  arrayRemove,
  arrayUnion,
  clearIndexedDbPersistence,
  collection,
  collectionGroup,
  connectFirestoreEmulator,
  deleteDoc,
  deleteField,
  disableNetwork,
  doc,
  enableNetwork,
  endAt,
  endBefore,
  getCountFromServer,
  getDoc,
  getDocs,
  getFirestore,
  increment,
  initializeFirestore,
  limit,
  limitToLast,
  memoryLocalCache,
  onSnapshot,
  or,
  orderBy,
  persistentLocalCache,
  persistentMultipleTabManager,
  persistentSingleTabManager,
  query,
  serverTimestamp,
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
  DeleteDocumentOptions,
  DocumentData,
  EnablePersistenceOptions,
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
  UpdateDocumentOptions,
  UseEmulatorOptions,
  WriteBatchOptions,
} from './definitions';
import { FieldValue } from './field-value';
import { GeoPoint } from './geopoint';
import { Timestamp } from './timestamp';

export class FirebaseFirestoreWeb
  extends WebPlugin
  implements FirebaseFirestorePlugin
{
  private readonly unsubscribesMap: Map<string, Unsubscribe> = new Map();

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
            data: this.deserializeData(documentSnapshot.data()) as T,
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
            data: this.deserializeData(documentSnapshot.data()) as T,
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

  public async addDocument(
    options: AddDocumentOptions,
  ): Promise<AddDocumentResult> {
    const firestore = getFirestore();
    const { reference, data } = options;
    const documentReference = await addDoc<DocumentData, DocumentData>(
      collection(firestore, reference),
      this.serializeData(data),
    );
    return {
      reference: {
        id: documentReference.id,
        path: documentReference.path,
      },
    };
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
            data: (data === undefined
              ? null
              : this.deserializeData(data)) as T | null,
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

  public async clearPersistence(): Promise<void> {
    const firestore = getFirestore();
    await clearIndexedDbPersistence(firestore);
  }

  public async deleteDocument(options: DeleteDocumentOptions): Promise<void> {
    const firestore = getFirestore();
    const { reference } = options;
    await deleteDoc(doc(firestore, reference));
  }

  public async disableNetwork(): Promise<void> {
    const firestore = getFirestore();
    await disableNetwork(firestore);
  }

  public async disablePersistence(): Promise<void> {
    initializeFirestore(getApp(), {
      localCache: memoryLocalCache(),
    });
  }

  public async enableNetwork(): Promise<void> {
    const firestore = getFirestore();
    await enableNetwork(firestore);
  }

  public async enablePersistence(
    options?: EnablePersistenceOptions,
  ): Promise<void> {
    const tabManager = options?.synchronizeTabs
      ? persistentMultipleTabManager()
      : persistentSingleTabManager(undefined);
    initializeFirestore(getApp(), {
      localCache: persistentLocalCache({
        tabManager,
        cacheSizeBytes: options?.cacheSizeBytes,
      }),
    });
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
        data: this.deserializeData(documentSnapshot.data()) as T,
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
        data: this.deserializeData(documentSnapshot.data()) as T,
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
          : this.deserializeData(documentSnapshotData)) as T | null,
        metadata: {
          hasPendingWrites: documentSnapshot.metadata.hasPendingWrites,
          fromCache: documentSnapshot.metadata.fromCache,
        },
      },
    };
  }

  public async removeAllListeners(): Promise<void> {
    this.unsubscribesMap.forEach(unsubscribe => unsubscribe());
    this.unsubscribesMap.clear();
    await super.removeAllListeners();
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

  public async setDocument(options: SetDocumentOptions): Promise<void> {
    const firestore = getFirestore();
    const { reference, data, merge } = options;
    await setDoc<DocumentData, DocumentData>(
      doc(firestore, reference),
      this.serializeData(data),
      {
        merge,
      },
    );
  }

  public async updateDocument(options: UpdateDocumentOptions): Promise<void> {
    const firestore = getFirestore();
    const { reference, data } = options;
    await updateDoc<DocumentData, DocumentData>(
      doc(firestore, reference),
      this.serializeData(data),
    );
  }

  public async useEmulator(options: UseEmulatorOptions): Promise<void> {
    const firestore = getFirestore();
    const port = options.port || 8080;
    connectFirestoreEmulator(firestore, options.host, port);
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
          batch.set(
            documentReference,
            this.serializeData(data ?? {}),
            options ?? {},
          );
          break;
        case 'update':
          batch.update(documentReference, this.serializeData(data ?? {}));
          break;
        case 'delete':
          batch.delete(documentReference);
          break;
      }
    }
    await batch.commit();
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

  private async buildFirebaseQueryConstraint(
    queryConstraint: QueryConstraint,
  ): Promise<FirebaseQueryConstraint> {
    if (queryConstraint.type === 'where') {
      return this.buildFirebaseQueryFieldFilterConstraint(queryConstraint);
    } else {
      return await this.buildFirebaseQueryNonFilterConstraint(queryConstraint);
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

  private buildFirebaseQueryFieldFilterConstraint(
    queryfilterConstraints: QueryFieldFilterConstraint,
  ): FirebaseQueryFieldFilterConstraint {
    return where(
      queryfilterConstraints.fieldPath,
      queryfilterConstraints.opStr,
      this.serializeFilterValue(queryfilterConstraints.value),
    );
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

  private deserializeData(data: any): any {
    if (data === null || data === undefined) {
      return data;
    }
    if (data instanceof FirebaseTimestamp) {
      return {
        __type__: 'timestamp',
        seconds: data.seconds,
        nanoseconds: data.nanoseconds,
      };
    }
    if (data instanceof FirebaseGeoPoint) {
      return {
        __type__: 'geopoint',
        latitude: data.latitude,
        longitude: data.longitude,
      };
    }
    if (Array.isArray(data)) {
      return data.map(item => this.deserializeData(item));
    }
    if (typeof data === 'object') {
      const result: Record<string, any> = {};
      for (const key of Object.keys(data)) {
        result[key] = this.deserializeData(data[key]);
      }
      return result;
    }
    return data;
  }

  private serializeData(data: any): any {
    if (data instanceof Timestamp) {
      return new FirebaseTimestamp(data.seconds, data.nanoseconds);
    }
    if (data instanceof GeoPoint) {
      return new FirebaseGeoPoint(data.latitude, data.longitude);
    }
    if (data instanceof FieldValue) {
      return this.serializeFieldValue(data.toJSON());
    }
    if (data === null || data === undefined) {
      return data;
    }
    if (Array.isArray(data)) {
      return data.map(item => this.serializeData(item));
    }
    if (typeof data === 'object') {
      if (data.__type__) {
        return this.serializeMarker(data);
      }
      const result: Record<string, any> = {};
      for (const key of Object.keys(data)) {
        result[key] = this.serializeData(data[key]);
      }
      return result;
    }
    return data;
  }

  private serializeFieldValue(marker: any): any {
    return this.serializeMarker(marker);
  }

  private serializeFilterValue(value: any): any {
    return this.serializeData(value);
  }

  private serializeMarker(marker: any): any {
    switch (marker.__type__) {
      case 'timestamp':
        return new FirebaseTimestamp(marker.seconds, marker.nanoseconds);
      case 'geopoint':
        return new FirebaseGeoPoint(marker.latitude, marker.longitude);
      case 'serverTimestamp':
        return serverTimestamp();
      case 'arrayUnion':
        return arrayUnion(
          ...(marker.elements || []).map((e: any) => this.serializeData(e)),
        );
      case 'arrayRemove':
        return arrayRemove(
          ...(marker.elements || []).map((e: any) => this.serializeData(e)),
        );
      case 'delete':
        return deleteField();
      case 'increment':
        return increment(marker.operand);
      default:
        return marker;
    }
  }
}
