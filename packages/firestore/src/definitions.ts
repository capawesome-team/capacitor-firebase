export interface FirebaseFirestorePlugin {
  /**
   * Adds a new document to a collection with the given data.
   *
   * @since 5.2.0
   */
  addDocument(options: AddDocumentOptions): Promise<AddDocumentResult>;
  /**
   * Writes to the document referred to by the specified reference.
   * If the document does not yet exist, it will be created.
   *
   * @since 5.2.0
   */
  setDocument(options: SetDocumentOptions): Promise<void>;
  /**
   * Reads the document referred to by the specified reference.
   *
   * @since 5.2.0
   */
  getDocument<T extends DocumentData = DocumentData>(
    options: GetDocumentOptions,
  ): Promise<GetDocumentResult<T>>;
  /**
   * Updates fields in the document referred to by the specified reference.
   *
   * @since 5.2.0
   */
  updateDocument(options: UpdateDocumentOptions): Promise<void>;
  /**
   * Deletes the document referred to by the specified reference.
   *
   * @since 5.2.0
   */
  deleteDocument(options: DeleteDocumentOptions): Promise<void>;
  /**
   * Execute multiple write operations as a single batch.
   *
   * @since 6.1.0
   */
  writeBatch(options: WriteBatchOptions): Promise<void>;
  /**
   * Reads the collection referenced by the specified reference.
   *
   * @since 5.2.0
   */
  getCollection<T extends DocumentData = DocumentData>(
    options: GetCollectionOptions,
  ): Promise<GetCollectionResult<T>>;
  /**
   * Reads the collection group referenced by the specified reference.
   */
  getCollectionGroup<T extends DocumentData = DocumentData>(
    options: GetCollectionGroupOptions,
  ): Promise<GetCollectionGroupResult<T>>;
  /**
   * Clears the persistent storage. This includes pending writes and cached documents.
   *
   * Must be called after the app is shutdown or when the app is first initialized.
   *
   * @since 5.2.0
   */
  clearPersistence(): Promise<void>;
  /**
   * Re-enables use of the network.
   *
   * @since 5.2.0
   */
  enableNetwork(): Promise<void>;
  /**
   * Disables use of the network.
   *
   * @since 5.2.0
   */
  disableNetwork(): Promise<void>;
  /**
   * Instrument your app to talk to the Firestore emulator.
   *
   * @since 6.1.0
   */
  useEmulator(options: UseEmulatorOptions): Promise<void>;
  /**
   * Adds a listener for document snapshot events.
   *
   * @since 5.2.0
   */
  addDocumentSnapshotListener<T extends DocumentData = DocumentData>(
    options: AddDocumentSnapshotListenerOptions,
    callback: AddDocumentSnapshotListenerCallback<T>,
  ): Promise<CallbackId>;
  /**
   * Adds a listener for collection snapshot events.
   *
   * @since 5.2.0
   */
  addCollectionSnapshotListener<T extends DocumentData = DocumentData>(
    options: AddCollectionSnapshotListenerOptions,
    callback: AddCollectionSnapshotListenerCallback<T>,
  ): Promise<CallbackId>;
  /**
   * Adds a listener for collection group snapshot events.
   *
   * @since 6.1.0
   */
  addCollectionGroupSnapshotListener<T extends DocumentData = DocumentData>(
    options: AddCollectionGroupSnapshotListenerOptions,
    callback: AddCollectionGroupSnapshotListenerCallback<T>,
  ): Promise<CallbackId>;
  /**
   * Remove a listener for document or collection snapshot events.
   *
   * @since 5.2.0
   */
  removeSnapshotListener(options: RemoveSnapshotListenerOptions): Promise<void>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 5.2.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 5.2.0
 */
export interface SetDocumentOptions {
  /**
   * The reference as a string, with path components separated by a forward slash (`/`).
   *
   * @since 5.2.0
   * @example 'users/Aorq09lkt1ynbR7xhTUx'
   */
  reference: string;
  /**
   * An object containing the data for the new document.
   *
   * @since 5.2.0
   * @example { first: 'Alan', last: 'Turing', born: 1912 }
   */
  data: DocumentData;
  /**
   * Whether to merge the provided data with an existing document.
   *
   * @since 5.2.0
   * @example true
   * @default false
   */
  merge?: boolean;
}

/**
 * @since 5.2.0
 */
export interface AddDocumentOptions {
  /**
   * The reference as a string, with path components separated by a forward slash (`/`).
   *
   * @since 5.2.0
   * @example 'users'
   */
  reference: string;
  /**
   * An object containing the data for the new document.
   *
   * @since 5.2.0
   * @example { first: 'Alan', last: 'Turing', born: 1912 }
   */
  data: DocumentData;
}

/**
 * @since 5.2.0
 */
export interface AddDocumentResult {
  /**
   * The reference of the newly added document.
   *
   * @since 5.2.0
   */
  reference: DocumentReference;
}

/**
 * @since 5.2.0
 */
export interface GetDocumentOptions {
  /**
   * The reference as a string, with path components separated by a forward slash (`/`).
   *
   * @since 5.2.0
   */
  reference: string;
}

/**
 * @since 5.2.0
 */
export interface GetDocumentResult<T> {
  /**
   * The current document contents.
   *
   * @since 5.2.0
   */
  snapshot: DocumentSnapshot<T>;
}

/**
 * @since 5.2.0
 */
export interface UpdateDocumentOptions {
  /**
   * The reference as a string, with path components separated by a forward slash (`/`).
   *
   * @since 5.2.0
   */
  reference: string;
  /**
   * An object containing the data for the new document.
   *
   * @since 5.2.0
   * @example { first: 'Alan', last: 'Turing', born: 1912 }
   */
  data: DocumentData;
}

/**
 * @since 5.2.0
 */
export interface DeleteDocumentOptions {
  /**
   * The reference as a string, with path components separated by a forward slash (`/`).
   *
   * @since 5.2.0
   */
  reference: string;
}

/**
 * @since 6.1.0
 */
export interface WriteBatchOptions {
  /**
   * The operations to execute in the batch.
   *
   * @since 6.1.0
   */
  operations: WriteBatchOperation[];
}

/**
 * @since 6.1.0
 */
export interface WriteBatchOperation {
  /**
   * The type of operation.
   *
   * @since 6.1.0
   */
  type: 'set' | 'update' | 'delete';
  /**
   * The reference as a string, with path components separated by a forward slash (`/`).
   *
   * @since 6.1.0
   */
  reference: string;
  /**
   * An object containing the data for the new document.
   *
   * @since 6.1.0
   */
  data?: DocumentData;
}

/**
 * @since 5.2.0
 */
export interface GetCollectionOptions {
  /**
   * The reference as a string, with path components separated by a forward slash (`/`).
   *
   * @since 5.2.0
   */
  reference: string;
  /**
   * The filter to apply.
   *
   * @since 5.2.0
   */
  compositeFilter?: QueryCompositeFilterConstraint;
  /**
   * Narrow or order the set of documents to retrieve, but do not explicitly filter for document fields.
   *
   * @since 5.2.0
   */
  queryConstraints?: QueryNonFilterConstraint[];
}

/**
 * @since 5.2.0
 */
export interface GetCollectionResult<T> {
  /**
   * The documents in the collection.
   *
   * @since 5.2.0
   */
  snapshots: DocumentSnapshot<T>[];
}

/**
 * @since 5.2.0
 */
export interface GetCollectionGroupOptions {
  /**
   * The reference as a string, with path components separated by a forward slash (`/`).
   *
   * @since 5.2.0
   */
  reference: string;
  /**
   * The filter to apply.
   *
   * @since 5.2.0
   */
  compositeFilter?: QueryCompositeFilterConstraint;
  /**
   * Narrow or order the set of documents to retrieve, but do not explicitly filter for document fields.
   *
   * @since 5.2.0
   */
  queryConstraints?: QueryNonFilterConstraint[];
}

/**
 * @since 5.2.0
 */
export interface GetCollectionGroupResult<T> {
  /**
   * The documents in the collection.
   *
   * @since 5.2.0
   */
  snapshots: DocumentSnapshot<T>[];
}

/**
 * @since 6.1.0
 */
export interface UseEmulatorOptions {
  /**
   * The emulator host without any port or scheme.
   *
   * Note when using a Android Emulator device: 10.0.2.2 is the special IP address to connect to the 'localhost' of the host computer.
   *
   * @since 6.1.0
   * @example "127.0.0.1"
   */
  host: string;
  /**
   * The emulator port.
   *
   * @since 6.1.0
   * @default 8080
   * @example 8080
   */
  port?: number;
}

/**
 * @since 6.2.0
 */
export interface SnapshotListenerOptions {
  /**
   * Include a change even if only the metadata of the query or of a document
   * changed.
   *
   * @since 6.2.0
   * @default false
   */
  readonly includeMetadataChanges?: boolean;
  /**
   * Set the source the query listens to.
   * The source `default` listens to both cache and server.
   *
   * Only available for Web.
   *
   * @since 6.2.0
   * @default "default"
   */
  readonly source?: 'default' | 'cache';
}

/**
 * @since 5.2.0
 */
export interface AddDocumentSnapshotListenerOptions
  extends SnapshotListenerOptions {
  /**
   * The reference as a string, with path components separated by a forward slash (`/`).
   *
   * @since 5.2.0
   */
  reference: string;
}

/**
 * @since 5.2.0
 */
export type AddDocumentSnapshotListenerCallback<T> = (
  event: AddDocumentSnapshotListenerCallbackEvent<T> | null,
  error: any,
) => void;

/**
 * @since 5.2.0
 */
export type AddDocumentSnapshotListenerCallbackEvent<T> = GetDocumentResult<T>;

/**
 * @since 5.2.0
 */
export interface AddCollectionSnapshotListenerOptions
  extends SnapshotListenerOptions {
  /**
   * The reference as a string, with path components separated by a forward slash (`/`).
   *
   * @since 5.2.0
   */
  reference: string;
  /**
   * The filter to apply.
   *
   * @since 5.2.0
   */
  compositeFilter?: QueryCompositeFilterConstraint;
  /**
   * Narrow or order the set of documents to retrieve, but do not explicitly filter for document fields.
   *
   * @since 5.2.0
   */
  queryConstraints?: QueryNonFilterConstraint[];
}

/**
 * @since 5.2.0
 */
export type AddCollectionSnapshotListenerCallback<T> = (
  event: AddCollectionSnapshotListenerCallbackEvent<T> | null,
  error: any,
) => void;

/**
 * @since 5.2.0
 */
export type AddCollectionSnapshotListenerCallbackEvent<T> =
  GetCollectionResult<T>;

/**
 * @since 6.1.0
 */
export interface AddCollectionGroupSnapshotListenerOptions
  extends SnapshotListenerOptions {
  /**
   * The reference as a string, with path components separated by a forward slash (`/`).
   *
   * @since 6.1.0
   */
  reference: string;
  /**
   * The filter to apply.
   *
   * @since 6.1.0
   */
  compositeFilter?: QueryCompositeFilterConstraint;
  /**
   * Narrow or order the set of documents to retrieve, but do not explicitly filter for document fields.
   *
   * @since 6.1.0
   */
  queryConstraints?: QueryNonFilterConstraint[];
}

/**
 * @since 6.1.0
 */
export type AddCollectionGroupSnapshotListenerCallback<T> = (
  event: AddCollectionGroupSnapshotListenerCallbackEvent<T> | null,
  error: any,
) => void;

/**
 * @since 6.1.0
 */
export type AddCollectionGroupSnapshotListenerCallbackEvent<T> =
  GetCollectionGroupResult<T>;

/**
 * @since 5.2.0
 */
export type CallbackId = string;

/**
 * @since 5.2.0
 */
export interface RemoveSnapshotListenerOptions {
  /**
   * @since 5.2.0
   */
  callbackId: CallbackId;
}

/**
 * @since 5.2.0
 */
export interface DocumentReference {
  /**
   * The document's identifier within its collection.
   *
   * @since 5.2.0
   * @example 'Aorq09lkt1ynbR7xhTUx'
   */
  id: string;
  /**
   * The path of the document.
   *
   * @since 5.2.0
   * @example 'users/Aorq09lkt1ynbR7xhTUx'
   */
  path: string;
}

/**
 * @since 5.2.0
 */
export interface DocumentSnapshot<T> {
  /**
   * The document's identifier within its collection.
   *
   * @since 5.2.0
   */
  id: string;
  /**
   * The path of the document.
   *
   * @since 5.2.0
   */
  path: string;
  /**
   * An object containing the data for the document.
   *
   * Returns `null` if the document doesn't exist.
   *
   * @since 5.2.0
   */
  data: T | null;
  /**
   * Metadata about the snapshot, concerning its source and if it has local modifications.
   *
   * @since 6.2.0
   */
  metadata: SnapshotMetadata;
}

/**
 * @since 6.2.0
 */
export interface SnapshotMetadata {
  /**
   * True if the snapshot was created from cached data.
   *
   * @since 6.2.0
   */
  fromCache: boolean;
  /**
   * True if the snapshot was created from pending write data.
   *
   * @since 6.2.0
   */
  hasPendingWrites: boolean;
}

/**
 * @since 5.2.0
 */
export type QueryFilterConstraint =
  | QueryFieldFilterConstraint
  | QueryCompositeFilterConstraint;

/**
 * @since 5.2.0
 */
export type QueryNonFilterConstraint =
  | QueryOrderByConstraint
  | QueryLimitConstraint
  | QueryStartAtConstraint
  | QueryEndAtConstraint;

/**
 * @since 5.2.0
 */
export interface QueryCompositeFilterConstraint {
  /**
   * The type of the constraint.
   *
   * @since 5.2.0
   */
  type: 'and' | 'or';
  /**
   * The filters to apply.
   *
   * @since 5.2.0
   */
  queryConstraints: QueryFilterConstraint[];
}

/**
 * @since 5.2.0
 */
export type QueryConstraint =
  | QueryFieldFilterConstraint
  | QueryNonFilterConstraint;

/**
 * @since 5.2.0
 */
export interface QueryFieldFilterConstraint {
  /**
   * The type of the constraint.
   *
   * @since 5.2.0
   */
  type: 'where';
  /**
   * The path to compare.
   *
   * @since 5.2.0
   */
  fieldPath: string;
  /**
   * The operation string to apply.
   *
   * @since 5.2.0
   */
  opStr: QueryOperator;
  /**
   * The value for comparison.
   *
   * @since 5.2.0
   */
  value: any;
}

/**
 * @since 5.2.0
 */
export interface QueryOrderByConstraint {
  /**
   * The type of the constraint.
   *
   * @since 5.2.0
   */
  type: 'orderBy';
  /**
   * The path to compare.
   *
   * @since 5.2.0
   */
  fieldPath: string;
  /**
   * The direction to sort by.
   *
   * @since 5.2.0
   */
  directionStr: OrderByDirection;
}

/**
 * @since 5.2.0
 */
export interface QueryLimitConstraint {
  /**
   * The type of the constraint.
   *
   * @since 5.2.0
   */
  type: 'limit' | 'limitToLast';
  /**
   * The maximum number of items to return.
   *
   * @since 5.2.0
   */
  limit: number;
}

/**
 * @since 5.2.0
 */
export interface QueryStartAtConstraint {
  /**
   * The type of the constraint.
   *
   * @since 5.2.0
   */
  type: 'startAt' | 'startAfter';
  /**
   * The reference to start at or after as a string, with path components separated by a forward slash (`/`).
   *
   * **Attention**: This requires an additional document read.
   *
   * @since 5.2.0
   * @example 'users/Aorq09lkt1ynbR7xhTUx'
   */
  reference: string;
}

/**
 * @since 5.2.0
 */
export interface QueryEndAtConstraint {
  /**
   * The type of the constraint.
   *
   * @since 5.2.0
   */
  type: 'endAt' | 'endBefore';
  /**
   * The reference as to end at or before as a string, with path components separated by a forward slash (`/`).
   *
   * **Attention**: This requires an additional document read.
   *
   * @since 5.2.0
   * @example 'users/Aorq09lkt1ynbR7xhTUx'
   */
  reference: string;
}

/**
 * @since 5.2.0
 */
export interface DocumentData {
  /**
   * A mapping between a field and its value.
   *
   * @since 5.2.0
   */
  [field: string]: any;
}

/**
 * @since 5.2.0
 */
export type QueryOperator =
  | '<'
  | '<='
  | '=='
  | '>='
  | '>'
  | '!='
  | 'array-contains'
  | 'array-contains-any'
  | 'in'
  | 'not-in';

/**
 * @since 5.2.0
 */
export type QueryConstraintType =
  | 'where'
  | 'orderBy'
  | 'limit'
  | 'limitToLast'
  | 'startAt'
  | 'startAfter'
  | 'endAt'
  | 'endBefore';

/**
 * @since 5.2.0
 */
export type OrderByDirection = 'desc' | 'asc';
