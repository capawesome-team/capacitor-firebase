import { WebPlugin } from '@capacitor/core';
import { getApp } from 'firebase/app';
import type {
  DocumentSnapshot as FirebaseDocumentSnapshot,
  QueryCompositeFilterConstraint as FirebaseQueryCompositeFilterConstraint,
  QueryConstraint as FirebaseQueryConstraint,
  QueryFieldFilterConstraint as FirebaseQueryFieldFilterConstraint,
  QueryFilterConstraint as FirebaseQueryFilterConstraint,
  QueryNonFilterConstraint as FirebaseQueryNonFilterConstraint,
  Query,
  SnapshotOptions,
  Unsubscribe,
} from 'firebase/firestore';
import {
  Bytes as FirebaseBytes,
  DocumentReference as FirebaseDocumentReference,
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
  ServerTimestampBehavior,
  SetDocumentOptions,
  UpdateDocumentOptions,
  UseEmulatorOptions,
  WriteBatchOptions,
} from './definitions';
import { FieldValue } from './field-value';
import { GeoPoint } from './geopoint';
import { Timestamp } from './timestamp';
import { deserializeNonFiniteValue, serializeNonFiniteValue } from './utils';

export class FirebaseFirestoreWeb
  extends WebPlugin
  implements FirebaseFirestorePlugin
{
  private readonly unsubscribesMap: Map<string, Unsubscribe> = new Map();
  /**
   * Monotonic counter used to generate unique listener IDs.
   */
  private listenerIdCounter = 0;

  private nextListenerId(): string {
    this.listenerIdCounter += 1;
    return this.listenerIdCounter.toString();
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
    const snapshotOptions = this.buildSnapshotOptions(options.serverTimestamps);
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
            data: this.deserializeData(
              this.readSnapshotData(documentSnapshot, snapshotOptions),
            ) as T,
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
    const id = this.nextListenerId();
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
    const snapshotOptions = this.buildSnapshotOptions(options.serverTimestamps);
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
            data: this.deserializeData(
              this.readSnapshotData(documentSnapshot, snapshotOptions),
            ) as T,
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
    const id = this.nextListenerId();
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
    const snapshotOptions = this.buildSnapshotOptions(options.serverTimestamps);
    const unsubscribe = onSnapshot(
      doc(firestore, options.reference),
      {
        includeMetadataChanges: options.includeMetadataChanges,
        source: options.source,
      },
      snapshot => {
        const data = this.readSnapshotData(snapshot, snapshotOptions);
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
    const id = this.nextListenerId();
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
    const snapshotOptions = this.buildSnapshotOptions(options.serverTimestamps);
    return {
      snapshots: collectionSnapshot.docs.map(documentSnapshot => ({
        id: documentSnapshot.id,
        path: documentSnapshot.ref.path,
        data: this.deserializeData(
          this.readSnapshotData(documentSnapshot, snapshotOptions),
        ) as T,
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
    const snapshotOptions = this.buildSnapshotOptions(options.serverTimestamps);
    return {
      snapshots: collectionSnapshot.docs.map(documentSnapshot => ({
        id: documentSnapshot.id,
        path: documentSnapshot.ref.path,
        data: this.deserializeData(
          this.readSnapshotData(documentSnapshot, snapshotOptions),
        ) as T,
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
    // Accept the same `compositeFilter`/`queryConstraints` shape as
    // `getCollection`, so callers can count a filtered query rather than only
    // an entire collection.
    const hasQuery =
      options.compositeFilter != null ||
      (options.queryConstraints != null &&
        options.queryConstraints.length > 0);
    const countQuery = hasQuery
      ? await this.buildCollectionQuery(
          {
            reference: options.reference,
            compositeFilter: options.compositeFilter,
            queryConstraints: options.queryConstraints,
          },
          'collection',
        )
      : collection(getFirestore(), options.reference);
    const snapshot = await getCountFromServer(countQuery);
    return { count: snapshot.data().count };
  }

  public async getDocument<T extends DocumentData>(
    options: GetDocumentOptions,
  ): Promise<GetDocumentResult<T>> {
    const firestore = getFirestore();
    const { reference } = options;
    const documentSnapshot = await getDoc(doc(firestore, reference));
    const snapshotOptions = this.buildSnapshotOptions(options.serverTimestamps);
    const documentSnapshotData = this.readSnapshotData(
      documentSnapshot,
      snapshotOptions,
    );
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

  /**
   * Converts JS SDK class instances inside a snapshot's data into the plain
   * `{ __type__: ... }` marker objects callers consume. A `WeakSet` cycle
   * guard prevents the recursive walk from following self-referential
   * properties that the JS SDK builds on some classes (notably
   * `DocumentReference._firestore` → `... → documentReference`), which would
   * otherwise stack-overflow when a document contains a DocumentReference
   * field.
   *
   * Each known class (`Timestamp`, `GeoPoint`, `DocumentReference`, `Bytes`)
   * is checked before the generic object walk. Unknown class instances are
   * preserved unchanged — falling through to the object walk would also visit
   * their internal fields and is rarely what callers want.
   */
  private deserializeData(data: any, seen: WeakSet<object> = new WeakSet()): any {
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
    if (data instanceof FirebaseDocumentReference) {
      return {
        __type__: 'documentReference',
        id: data.id,
        path: data.path,
      };
    }
    if (data instanceof FirebaseBytes) {
      return {
        __type__: 'bytes',
        base64: data.toBase64(),
      };
    }
    if (typeof data === 'number' && !Number.isFinite(data)) {
      return {
        __type__: 'double',
        value: serializeNonFiniteValue(data),
      };
    }
    if (typeof data !== 'object') {
      return data;
    }
    if (seen.has(data)) {
      return null;
    }
    seen.add(data);
    if (Array.isArray(data)) {
      return data.map(item => this.deserializeData(item, seen));
    }
    // Only walk plain objects. Class instances we don't recognize (e.g. an
    // internal JS SDK type that leaked through) are preserved as-is rather
    // than having their private fields traversed.
    const proto = Object.getPrototypeOf(data);
    if (proto !== Object.prototype && proto !== null) {
      return data;
    }
    const result: Record<string, any> = {};
    for (const key of Object.keys(data)) {
      result[key] = this.deserializeData(data[key], seen);
    }
    return result;
  }

  /**
   * Converts caller-supplied data into the JS SDK shape `setDoc`/`updateDoc`
   * expect. Known JS SDK class instances are handled explicitly so the
   * generic object walk never tries to recurse into the cyclic internals
   * (`_firestore`, `_query`, ...) of types like `DocumentReference`. `Date`
   * values are reshaped into the plugin's `timestamp` marker because the
   * Capacitor bridge would otherwise serialize them as empty objects.
   */
  private serializeData(data: any, seen: WeakSet<object> = new WeakSet()): any {
    if (data === null || data === undefined) {
      return data;
    }
    if (data instanceof Timestamp) {
      return new FirebaseTimestamp(data.seconds, data.nanoseconds);
    }
    if (data instanceof FirebaseTimestamp) {
      return data;
    }
    if (data instanceof Date) {
      return FirebaseTimestamp.fromDate(data);
    }
    if (data instanceof GeoPoint) {
      return new FirebaseGeoPoint(data.latitude, data.longitude);
    }
    if (data instanceof FirebaseGeoPoint) {
      return data;
    }
    if (data instanceof FirebaseDocumentReference) {
      return data;
    }
    if (data instanceof FirebaseBytes) {
      return data;
    }
    if (data instanceof FieldValue) {
      return this.serializeFieldValue(data.toJSON());
    }
    if (Array.isArray(data)) {
      if (seen.has(data)) return null;
      seen.add(data);
      return data.map(item => this.serializeData(item, seen));
    }
    if (typeof data === 'object') {
      if (data.__type__) {
        return this.serializeMarker(data);
      }
      if (seen.has(data)) return null;
      seen.add(data);
      const proto = Object.getPrototypeOf(data);
      if (proto !== Object.prototype && proto !== null) {
        // Unknown class instance — pass through unchanged so the JS SDK can
        // either accept it or report a clear validation error. Walking its
        // internals usually causes a stack overflow or produces a malformed
        // payload.
        return data;
      }
      const result: Record<string, any> = {};
      for (const key of Object.keys(data)) {
        result[key] = this.serializeData(data[key], seen);
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
      case 'documentReference':
        return doc(getFirestore(), marker.path);
      case 'bytes':
        return FirebaseBytes.fromBase64String(marker.base64);
      case 'double':
        return deserializeNonFiniteValue(marker.value);
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

  /**
   * Wraps the provided behavior in the JS SDK's `SnapshotOptions` shape, or
   * returns `undefined` when no override was requested so the SDK uses its
   * default `'none'` behavior.
   */
  private buildSnapshotOptions(
    serverTimestamps: ServerTimestampBehavior | undefined,
  ): SnapshotOptions | undefined {
    return serverTimestamps ? { serverTimestamps } : undefined;
  }

  /**
   * Reads a snapshot's data with the provided behavior. Centralized so the
   * `serverTimestamps` option doesn't have to be threaded through every
   * caller, and so a future option can be added in one place.
   */
  private readSnapshotData<T extends DocumentData>(
    snapshot: FirebaseDocumentSnapshot<T, DocumentData>,
    options: SnapshotOptions | undefined,
  ): T | undefined {
    return options ? snapshot.data(options) : snapshot.data();
  }
}
