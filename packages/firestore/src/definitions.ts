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
  snapshots: QueryDocumentSnapshot<T>[];
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
  snapshots: QueryDocumentSnapshot<T>[];
}

/**
 * @since 5.2.0
 */
export interface AddDocumentSnapshotListenerOptions {
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
  event: AddDocumentSnapshotListenerEvent<T>,
  err?: any,
) => void;

/**
 * @since 5.2.0
 */
export type AddDocumentSnapshotListenerEvent<T> = GetDocumentResult<T>;

/**
 * @since 5.2.0
 */
export interface AddCollectionSnapshotListenerOptions {
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
  event: AddCollectionSnapshotListenerEvent<T>,
  err?: any,
) => void;

/**
 * @since 5.2.0
 */
export type AddCollectionSnapshotListenerEvent<T> = GetCollectionResult<T>;

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
}

/**
 * @since 5.2.0
 */
export interface QueryDocumentSnapshot<T> {
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
   * @since 5.2.0
   */
  data: T;
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
  value: string | number | boolean | null;
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
  | 'array-contains';
// | 'array-contains-any'
// | 'in'
// | 'not-in';

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
