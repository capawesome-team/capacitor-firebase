import { WebPlugin } from '@capacitor/core';
import { addDoc, and, clearIndexedDbPersistence, collection, collectionGroup, connectFirestoreEmulator, deleteDoc, disableNetwork, doc, enableNetwork, endAt, endBefore, getCountFromServer, getDoc, getDocs, getFirestore, limit, limitToLast, onSnapshot, or, orderBy, query, setDoc, startAfter, startAt, updateDoc, where, writeBatch, } from 'firebase/firestore';
export class FirebaseFirestoreWeb extends WebPlugin {
    constructor() {
        super(...arguments);
        this.unsubscribesMap = new Map();
    }
    async addDocument(options) {
        const firestore = getFirestore();
        const { reference, data } = options;
        const documentReference = await addDoc(collection(firestore, reference), data);
        return {
            reference: {
                id: documentReference.id,
                path: documentReference.path,
            },
        };
    }
    async setDocument(options) {
        const firestore = getFirestore();
        const { reference, data, merge } = options;
        await setDoc(doc(firestore, reference), data, {
            merge,
        });
    }
    async getDocument(options) {
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
                    : documentSnapshotData),
                metadata: {
                    hasPendingWrites: documentSnapshot.metadata.hasPendingWrites,
                    fromCache: documentSnapshot.metadata.fromCache,
                },
            },
        };
    }
    async updateDocument(options) {
        const firestore = getFirestore();
        const { reference, data } = options;
        await updateDoc(doc(firestore, reference), data);
    }
    async deleteDocument(options) {
        const firestore = getFirestore();
        const { reference } = options;
        await deleteDoc(doc(firestore, reference));
    }
    async writeBatch(options) {
        const firestore = getFirestore();
        const { operations } = options;
        const batch = writeBatch(firestore);
        for (const operation of operations) {
            const { type, reference, data } = operation;
            const documentReference = doc(firestore, reference);
            switch (type) {
                case 'set':
                    batch.set(documentReference, data);
                    break;
                case 'update':
                    batch.update(documentReference, data !== null && data !== void 0 ? data : {});
                    break;
                case 'delete':
                    batch.delete(documentReference);
                    break;
            }
        }
        await batch.commit();
    }
    async getCollection(options) {
        const collectionQuery = await this.buildCollectionQuery(options, 'collection');
        const collectionSnapshot = await getDocs(collectionQuery);
        return {
            snapshots: collectionSnapshot.docs.map(documentSnapshot => ({
                id: documentSnapshot.id,
                path: documentSnapshot.ref.path,
                data: documentSnapshot.data(),
                metadata: {
                    hasPendingWrites: documentSnapshot.metadata.hasPendingWrites,
                    fromCache: documentSnapshot.metadata.fromCache,
                },
            })),
        };
    }
    async getCollectionGroup(options) {
        const collectionQuery = await this.buildCollectionQuery(options, 'collectionGroup');
        const collectionSnapshot = await getDocs(collectionQuery);
        return {
            snapshots: collectionSnapshot.docs.map(documentSnapshot => ({
                id: documentSnapshot.id,
                path: documentSnapshot.ref.path,
                data: documentSnapshot.data(),
                metadata: {
                    hasPendingWrites: documentSnapshot.metadata.hasPendingWrites,
                    fromCache: documentSnapshot.metadata.fromCache,
                },
            })),
        };
    }
    async getCountFromServer(options) {
        const firestore = getFirestore();
        const { reference } = options;
        const coll = collection(firestore, reference);
        const snapshot = await getCountFromServer(coll);
        return { count: snapshot.data().count };
    }
    async clearPersistence() {
        const firestore = getFirestore();
        await clearIndexedDbPersistence(firestore);
    }
    async enableNetwork() {
        const firestore = getFirestore();
        await enableNetwork(firestore);
    }
    async disableNetwork() {
        const firestore = getFirestore();
        await disableNetwork(firestore);
    }
    async useEmulator(options) {
        const firestore = getFirestore();
        const port = options.port || 8080;
        connectFirestoreEmulator(firestore, options.host, port);
    }
    async addDocumentSnapshotListener(options, callback) {
        const firestore = getFirestore();
        const unsubscribe = onSnapshot(doc(firestore, options.reference), {
            includeMetadataChanges: options.includeMetadataChanges,
            source: options.source,
        }, snapshot => {
            const data = snapshot.data();
            const event = {
                snapshot: {
                    id: snapshot.id,
                    path: snapshot.ref.path,
                    data: (data === undefined ? null : data),
                    metadata: {
                        hasPendingWrites: snapshot.metadata.hasPendingWrites,
                        fromCache: snapshot.metadata.fromCache,
                    },
                },
            };
            callback(event, undefined);
        }, error => callback(null, error));
        const id = Date.now().toString();
        this.unsubscribesMap.set(id, unsubscribe);
        return id;
    }
    async addCollectionSnapshotListener(options, callback) {
        const collectionQuery = await this.buildCollectionQuery(options, 'collection');
        const unsubscribe = onSnapshot(collectionQuery, {
            includeMetadataChanges: options.includeMetadataChanges,
            source: options.source,
        }, snapshot => {
            const event = {
                snapshots: snapshot.docs.map(documentSnapshot => ({
                    id: documentSnapshot.id,
                    path: documentSnapshot.ref.path,
                    data: documentSnapshot.data(),
                    metadata: {
                        hasPendingWrites: documentSnapshot.metadata.hasPendingWrites,
                        fromCache: documentSnapshot.metadata.fromCache,
                    },
                })),
            };
            callback(event, undefined);
        }, error => callback(null, error));
        const id = Date.now().toString();
        this.unsubscribesMap.set(id, unsubscribe);
        return id;
    }
    async addCollectionGroupSnapshotListener(options, callback) {
        const collectionQuery = await this.buildCollectionQuery(options, 'collectionGroup');
        const unsubscribe = onSnapshot(collectionQuery, {
            includeMetadataChanges: options.includeMetadataChanges,
            source: options.source,
        }, snapshot => {
            const event = {
                snapshots: snapshot.docs.map(documentSnapshot => ({
                    id: documentSnapshot.id,
                    path: documentSnapshot.ref.path,
                    data: documentSnapshot.data(),
                    metadata: {
                        hasPendingWrites: documentSnapshot.metadata.hasPendingWrites,
                        fromCache: documentSnapshot.metadata.fromCache,
                    },
                })),
            };
            callback(event, undefined);
        }, error => callback(null, error));
        const id = Date.now().toString();
        this.unsubscribesMap.set(id, unsubscribe);
        return id;
    }
    async removeSnapshotListener(options) {
        const unsubscribe = this.unsubscribesMap.get(options.callbackId);
        if (!unsubscribe) {
            return;
        }
        unsubscribe();
        this.unsubscribesMap.delete(options.callbackId);
    }
    async removeAllListeners() {
        this.unsubscribesMap.forEach(unsubscribe => unsubscribe());
        this.unsubscribesMap.clear();
        await super.removeAllListeners();
    }
    async buildCollectionQuery(options, type) {
        const firestore = getFirestore();
        let collectionQuery;
        if (options.compositeFilter) {
            const compositeFilter = this.buildFirebaseQueryCompositeFilterConstraint(options.compositeFilter);
            const queryConstraints = await this.buildFirebaseQueryNonFilterConstraints(options.queryConstraints || []);
            collectionQuery = query(type === 'collection'
                ? collection(firestore, options.reference)
                : collectionGroup(firestore, options.reference), compositeFilter, ...queryConstraints);
        }
        else {
            const queryConstraints = await this.buildFirebaseQueryConstraints(options.queryConstraints || []);
            collectionQuery = query(type === 'collection'
                ? collection(firestore, options.reference)
                : collectionGroup(firestore, options.reference), ...queryConstraints);
        }
        return collectionQuery;
    }
    buildFirebaseQueryCompositeFilterConstraint(compositeFilter) {
        const queryConstraints = this.buildFirebaseQueryFilterConstraints(compositeFilter.queryConstraints);
        if (compositeFilter.type === 'and') {
            return and(...queryConstraints);
        }
        else {
            return or(...queryConstraints);
        }
    }
    buildFirebaseQueryFilterConstraints(queryfilterConstraints) {
        const firebaseQueryFilterConstraints = [];
        for (const queryfilterConstraint of queryfilterConstraints) {
            const firebaseQueryFilterConstraint = this.buildFirebaseQueryFilterConstraint(queryfilterConstraint);
            firebaseQueryFilterConstraints.push(firebaseQueryFilterConstraint);
        }
        return firebaseQueryFilterConstraints;
    }
    buildFirebaseQueryFilterConstraint(queryFilterConstraints) {
        if (queryFilterConstraints.type === 'where') {
            return this.buildFirebaseQueryFieldFilterConstraint(queryFilterConstraints);
        }
        else {
            return this.buildFirebaseQueryCompositeFilterConstraint(queryFilterConstraints);
        }
    }
    buildFirebaseQueryFieldFilterConstraint(queryfilterConstraints) {
        return where(queryfilterConstraints.fieldPath, queryfilterConstraints.opStr, queryfilterConstraints.value);
    }
    async buildFirebaseQueryNonFilterConstraints(queryConstraints) {
        const firebaseQueryNonFilterConstraints = [];
        for (const queryConstraint of queryConstraints) {
            const firebaseQueryNonFilterConstraint = await this.buildFirebaseQueryNonFilterConstraint(queryConstraint);
            firebaseQueryNonFilterConstraints.push(firebaseQueryNonFilterConstraint);
        }
        return firebaseQueryNonFilterConstraints;
    }
    async buildFirebaseQueryNonFilterConstraint(queryConstraints) {
        switch (queryConstraints.type) {
            case 'orderBy':
                return orderBy(queryConstraints.fieldPath, queryConstraints.directionStr);
            case 'limit':
                return limit(queryConstraints.limit);
            case 'limitToLast':
                return limitToLast(queryConstraints.limit);
            case 'startAt':
            case 'startAfter':
            case 'endAt':
            case 'endBefore': {
                const firestore = getFirestore();
                const documentSnapshot = await getDoc(doc(firestore, queryConstraints.reference));
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
    async buildFirebaseQueryConstraints(queryConstraints) {
        const firebaseQueryConstraints = [];
        for (const queryConstraint of queryConstraints) {
            const firebaseQueryConstraint = await this.buildFirebaseQueryConstraint(queryConstraint);
            firebaseQueryConstraints.push(firebaseQueryConstraint);
        }
        return firebaseQueryConstraints;
    }
    async buildFirebaseQueryConstraint(queryConstraint) {
        if (queryConstraint.type === 'where') {
            return this.buildFirebaseQueryFieldFilterConstraint(queryConstraint);
        }
        else {
            return await this.buildFirebaseQueryNonFilterConstraint(queryConstraint);
        }
    }
}
//# sourceMappingURL=web.js.map