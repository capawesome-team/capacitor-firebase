var capacitorFirebaseFirestore = (function (exports, core, firestore) {
    'use strict';

    const FirebaseFirestore = core.registerPlugin('FirebaseFirestore', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FirebaseFirestoreWeb()),
    });

    class FirebaseFirestoreWeb extends core.WebPlugin {
        constructor() {
            super(...arguments);
            this.unsubscribesMap = new Map();
        }
        async addDocument(options) {
            const firestore$1 = firestore.getFirestore();
            const { reference, data } = options;
            const documentReference = await firestore.addDoc(firestore.collection(firestore$1, reference), data);
            return {
                reference: {
                    id: documentReference.id,
                    path: documentReference.path,
                },
            };
        }
        async setDocument(options) {
            const firestore$1 = firestore.getFirestore();
            const { reference, data, merge } = options;
            await firestore.setDoc(firestore.doc(firestore$1, reference), data, {
                merge,
            });
        }
        async getDocument(options) {
            const firestore$1 = firestore.getFirestore();
            const { reference } = options;
            const documentSnapshot = await firestore.getDoc(firestore.doc(firestore$1, reference));
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
            const firestore$1 = firestore.getFirestore();
            const { reference, data } = options;
            await firestore.updateDoc(firestore.doc(firestore$1, reference), data);
        }
        async deleteDocument(options) {
            const firestore$1 = firestore.getFirestore();
            const { reference } = options;
            await firestore.deleteDoc(firestore.doc(firestore$1, reference));
        }
        async writeBatch(options) {
            const firestore$1 = firestore.getFirestore();
            const { operations } = options;
            const batch = firestore.writeBatch(firestore$1);
            for (const operation of operations) {
                const { type, reference, data } = operation;
                const documentReference = firestore.doc(firestore$1, reference);
                switch (type) {
                    case 'set':
                        batch.set(documentReference, data);
                        break;
                    case 'update':
                        batch.update(documentReference, data !== null && data !== undefined ? data : {});
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
            const collectionSnapshot = await firestore.getDocs(collectionQuery);
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
            const collectionSnapshot = await firestore.getDocs(collectionQuery);
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
            const firestore$1 = firestore.getFirestore();
            const { reference } = options;
            const coll = firestore.collection(firestore$1, reference);
            const snapshot = await firestore.getCountFromServer(coll);
            return { count: snapshot.data().count };
        }
        async clearPersistence() {
            const firestore$1 = firestore.getFirestore();
            await firestore.clearIndexedDbPersistence(firestore$1);
        }
        async enableNetwork() {
            const firestore$1 = firestore.getFirestore();
            await firestore.enableNetwork(firestore$1);
        }
        async disableNetwork() {
            const firestore$1 = firestore.getFirestore();
            await firestore.disableNetwork(firestore$1);
        }
        async useEmulator(options) {
            const firestore$1 = firestore.getFirestore();
            const port = options.port || 8080;
            firestore.connectFirestoreEmulator(firestore$1, options.host, port);
        }
        async addDocumentSnapshotListener(options, callback) {
            const firestore$1 = firestore.getFirestore();
            const unsubscribe = firestore.onSnapshot(firestore.doc(firestore$1, options.reference), {
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
            const unsubscribe = firestore.onSnapshot(collectionQuery, {
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
            const unsubscribe = firestore.onSnapshot(collectionQuery, {
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
            const firestore$1 = firestore.getFirestore();
            let collectionQuery;
            if (options.compositeFilter) {
                const compositeFilter = this.buildFirebaseQueryCompositeFilterConstraint(options.compositeFilter);
                const queryConstraints = await this.buildFirebaseQueryNonFilterConstraints(options.queryConstraints || []);
                collectionQuery = firestore.query(type === 'collection'
                    ? firestore.collection(firestore$1, options.reference)
                    : firestore.collectionGroup(firestore$1, options.reference), compositeFilter, ...queryConstraints);
            }
            else {
                const queryConstraints = await this.buildFirebaseQueryConstraints(options.queryConstraints || []);
                collectionQuery = firestore.query(type === 'collection'
                    ? firestore.collection(firestore$1, options.reference)
                    : firestore.collectionGroup(firestore$1, options.reference), ...queryConstraints);
            }
            return collectionQuery;
        }
        buildFirebaseQueryCompositeFilterConstraint(compositeFilter) {
            const queryConstraints = this.buildFirebaseQueryFilterConstraints(compositeFilter.queryConstraints);
            if (compositeFilter.type === 'and') {
                return firestore.and(...queryConstraints);
            }
            else {
                return firestore.or(...queryConstraints);
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
            return firestore.where(queryfilterConstraints.fieldPath, queryfilterConstraints.opStr, queryfilterConstraints.value);
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
                    return firestore.orderBy(queryConstraints.fieldPath, queryConstraints.directionStr);
                case 'limit':
                    return firestore.limit(queryConstraints.limit);
                case 'limitToLast':
                    return firestore.limitToLast(queryConstraints.limit);
                case 'startAt':
                case 'startAfter':
                case 'endAt':
                case 'endBefore': {
                    const firestore$1 = firestore.getFirestore();
                    const documentSnapshot = await firestore.getDoc(firestore.doc(firestore$1, queryConstraints.reference));
                    switch (queryConstraints.type) {
                        case 'startAt':
                            return firestore.startAt(documentSnapshot);
                        case 'startAfter':
                            return firestore.startAfter(documentSnapshot);
                        case 'endAt':
                            return firestore.endAt(documentSnapshot);
                        case 'endBefore':
                            return firestore.endBefore(documentSnapshot);
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

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        FirebaseFirestoreWeb: FirebaseFirestoreWeb
    });

    exports.FirebaseFirestore = FirebaseFirestore;

    return exports;

})({}, capacitorExports, firebaseFirestoreExports);
//# sourceMappingURL=plugin.js.map
