import { Timestamp } from "firebase/firestore";
import { AddCollectionSnapshotListenerCallback, AddDocumentSnapshotListenerCallbackEvent, AddCollectionSnapshotListenerCallbackEvent, AddCollectionSnapshotListenerOptions, AddDocumentOptions, AddDocumentResult, AddDocumentSnapshotListenerCallback, AddDocumentSnapshotListenerOptions, DeleteDocumentOptions, DocumentData, GetCollectionGroupOptions, GetCollectionGroupResult, GetCollectionOptions, GetCollectionResult, GetDocumentOptions, GetDocumentResult, RemoveSnapshotListenerOptions, SetDocumentOptions, UpdateDocumentOptions } from "./definitions";
import { FirebaseFirestorePlugin } from "./definitions";

type SerializedTimeStamp = {
    type: 'timestamp',
    seconds: string, // parses to a number
    nanoseconds: string, // parses to a  number
}

function fromNativeTimestamp(ts:SerializedTimeStamp): Timestamp {
    let s = Number(ts.seconds);
    let n = Number(ts.nanoseconds);
    return new Timestamp(s, n);
}

function toNativeTimestamp(ts:Timestamp): SerializedTimeStamp {
    return {
        type: 'timestamp',
        seconds: Math.round(ts.seconds).toString(),
        nanoseconds: Math.round(ts.nanoseconds).toString(),
    }
}


/**
 * @param data DocumentSnapshot.data 
 */
function formatDocumentToNative(data:any): any {
    for (let key in data) {
        let val = data[key];
        // 1. format timestamps
        if (val instanceof Timestamp) {
            data[key] = toNativeTimestamp(val)
            continue;
        }

        // 2. format recursively on sub-objects
        if (val instanceof Object) {
            data[key] = formatDocumentToNative(val);
        }
    }
    return data;
}


/**
 * 
 * @param data DocumentSnapshot.data 
 * @returns 
 */
function formatDocumentFromNative(data:any): any {
    for (let key in data) {
        let val = data[key];

        // 1. format timestamps
        if (val && val.hasOwnProperty("type") && val.type == 'timestamp') {
            data[key] = fromNativeTimestamp(val)
            continue;
        }

        if (val instanceof Object) {
            data[key] = formatDocumentFromNative(val);
        }
    }
    return data;
}

export class FirebaseFirestoreNative implements FirebaseFirestorePlugin {
    nativeRef: FirebaseFirestorePlugin
    constructor(nativePlugin: FirebaseFirestorePlugin) {
        this.nativeRef = nativePlugin;
    }

    addDocument(options: AddDocumentOptions): Promise<AddDocumentResult> {
        // web -> native
        options.data = formatDocumentToNative(options.data);
        return this.nativeRef.addDocument(options);
    }

    setDocument(options: SetDocumentOptions): Promise<void> {
        // web -> native
        options.data = formatDocumentToNative(options.data);
        return this.nativeRef.setDocument(options);
    }

    getDocument<T extends DocumentData = DocumentData>(options: GetDocumentOptions): Promise<GetDocumentResult<T>> {
        console.log("NATIVE WRAPPER");
        return this.nativeRef.getDocument(options)
            .then(res => {
                // native -> web
                res.snapshot.data = formatDocumentFromNative(res.snapshot.data);
                return res as GetDocumentResult<T>;
            })
    }

    updateDocument(options: UpdateDocumentOptions): Promise<void> {
        // web -> native
        options.data = formatDocumentToNative(options.data);
        return this.nativeRef.updateDocument(options);
    }

    deleteDocument(options: DeleteDocumentOptions): Promise<void> {
        return this.nativeRef.deleteDocument(options);
    }

    getCollection<T extends DocumentData = DocumentData>(options: GetCollectionOptions): Promise<GetCollectionResult<T>> {
        return this.nativeRef.getCollection(options)
            .then(res => {
                // native -> web
                for (let snap of res.snapshots) {
                    snap.data = formatDocumentFromNative(snap.data);
                }
                return res as GetCollectionGroupResult<T>;
            });
    }

    getCollectionGroup<T extends DocumentData = DocumentData>(options: GetCollectionGroupOptions): Promise<GetCollectionGroupResult<T>> {
        return this.nativeRef.getCollectionGroup(options)
            .then(res => {
                // native -> web
                for (let snap of res.snapshots) {
                    snap.data = formatDocumentFromNative(snap.data);
                }
                return res as GetCollectionGroupResult<T>;
            });
    }

    clearPersistence(): Promise<void> {
        return this.nativeRef.clearPersistence();
    }

    enableNetwork(): Promise<void> {
        return this.nativeRef.enableNetwork();
    }

    disableNetwork(): Promise<void> {
        return this.nativeRef.disableNetwork();
    }

    addDocumentSnapshotListener<T extends DocumentData = DocumentData>(options: AddDocumentSnapshotListenerOptions, callback: AddDocumentSnapshotListenerCallback<T>): Promise<string> {
        return this.nativeRef.addDocumentSnapshotListener(options, (event:AddDocumentSnapshotListenerCallbackEvent<T> | null, error) => {
            if (event) {
                // native -> web
                event.snapshot.data = formatDocumentFromNative(event.snapshot.data);
            }
            callback(event, error);
        });
    }

    addCollectionSnapshotListener<T extends DocumentData = DocumentData>(options: AddCollectionSnapshotListenerOptions, callback: AddCollectionSnapshotListenerCallback<T>): Promise<string> {
        return this.nativeRef.addCollectionSnapshotListener(options, (event:AddCollectionSnapshotListenerCallbackEvent<T> | null, error) => {
            if (event) {
                // native -> web
                for (let snap of event.snapshots) {
                    snap.data = formatDocumentFromNative(snap.data);
                }
            }
            callback(event, error);
        });
    }

    removeSnapshotListener(options: RemoveSnapshotListenerOptions): Promise<void> {
        return this.nativeRef.removeSnapshotListener(options);
    }

    removeAllListeners(): Promise<void> {
        return this.nativeRef.removeAllListeners();
    }
}