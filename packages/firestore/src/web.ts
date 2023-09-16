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
  collection,
  deleteDoc,
  doc,
  getDoc,
  getDocs,
  getFirestore,
  limit,
  onSnapshot,
  or,
  orderBy,
  query,
  setDoc,
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
  SetDocumentOptions,
} from './definitions';

export class FirebaseFirestoreWeb
  extends WebPlugin
  implements FirebaseFirestorePlugin
{
  private readonly unsubscribes: Map<string, Unsubscribe> = new Map();

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
    return {
      snapshot: {
        id: documentSnapshot.id,
        path: documentSnapshot.ref.path,
        data: documentSnapshot.data() as T | undefined,
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
    const collectionQuery = this.buildCollectionQuery(options);
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
    const collectionQuery = this.buildCollectionQuery(options);
    const collectionSnapshot = await getDocs(collectionQuery);
    return {
      snapshots: collectionSnapshot.docs.map(documentSnapshot => ({
        id: documentSnapshot.id,
        path: documentSnapshot.ref.path,
        data: documentSnapshot.data() as T,
      })),
    };
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
        const event: AddDocumentSnapshotListenerEvent<T> = {
          snapshot: {
            id: snapshot.id,
            path: snapshot.ref.path,
            data: snapshot.data() as T | undefined,
          },
        };
        callback(event);
      },
    );
    const id = Date.now().toString();
    this.unsubscribes.set(id, unsubscribe);
    return id;
  }

  public async addCollectionSnapshotListener<
    T extends DocumentData = DocumentData,
  >(
    options: AddCollectionSnapshotListenerOptions,
    callback: AddCollectionSnapshotListenerCallback<T>,
  ): Promise<string> {
    const collectionQuery = this.buildCollectionQuery(options);
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
    this.unsubscribes.set(id, unsubscribe);
    return id;
  }

  private buildCollectionQuery(
    options:
      | GetCollectionOptions
      | GetCollectionGroupOptions
      | AddCollectionSnapshotListenerOptions, // TODO: merge types to CollectionQueryOptions
  ): Query<DocumentData, DocumentData> {
    const firestore = getFirestore();
    let collectionQuery: Query;
    if (options.compositeFilter) {
      const compositeFilter = this.buildFirebaseQueryCompositeFilterConstraint(
        options.compositeFilter,
      );
      const queryConstraints = this.buildFirebaseQueryNonFilterConstraints(
        options.queryConstraints || [],
      );
      collectionQuery = query(
        collection(firestore, options.reference),
        compositeFilter,
        ...queryConstraints,
      );
    } else {
      const queryConstraints = this.buildFirebaseQueryConstraints(
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

  private buildFirebaseQueryNonFilterConstraints(
    queryConstraints: QueryNonFilterConstraint[],
  ): FirebaseQueryNonFilterConstraint[] {
    const firebaseQueryNonFilterConstraints: FirebaseQueryNonFilterConstraint[] =
      [];
    for (const queryConstraint of queryConstraints) {
      const firebaseQueryNonFilterConstraint =
        this.buildFirebaseQueryNonFilterConstraint(queryConstraint);
      firebaseQueryNonFilterConstraints.push(firebaseQueryNonFilterConstraint);
    }
    return firebaseQueryNonFilterConstraints;
  }

  private buildFirebaseQueryNonFilterConstraint(
    queryConstraints: QueryNonFilterConstraint,
  ): FirebaseQueryNonFilterConstraint {
    if (queryConstraints.type === 'orderBy') {
      return orderBy(queryConstraints.fieldPath, queryConstraints.directionStr);
    } else {
      return limit(queryConstraints.limit);
    }
  }

  private buildFirebaseQueryConstraints(
    queryConstraints: QueryConstraint[],
  ): FirebaseQueryConstraint[] {
    const firebaseQueryConstraints: FirebaseQueryConstraint[] = [];
    for (const queryConstraint of queryConstraints) {
      const firebaseQueryConstraint =
        this.buildFirebaseQueryConstraint(queryConstraint);
      firebaseQueryConstraints.push(firebaseQueryConstraint);
    }
    return firebaseQueryConstraints;
  }

  private buildFirebaseQueryConstraint(
    queryConstraint: QueryConstraint,
  ): FirebaseQueryConstraint {
    if (queryConstraint.type === 'where') {
      return this.buildFirebaseQueryFieldFilterConstraint(queryConstraint);
    } else {
      return this.buildFirebaseQueryNonFilterConstraint(queryConstraint);
    }
  }
}
