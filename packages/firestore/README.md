# @capacitor-firebase/firestore

Unofficial Capacitor plugin for [Firebase Cloud Firestore](https://firebase.google.com/docs/firestore/).[^1]

## Installation

```bash
npm install @capacitor-firebase/firestore
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup)).

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseFirestoreVersion` version of `com.google.firebase:firebase-firestore` (default: `24.7.1`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebaseFirestore } from '@capacitor-firebase/firestore';

const echo = async () => {
  await FirebaseFirestore.echo();
};
```

## API

<docgen-index>

* [`addDocument(...)`](#adddocument)
* [`setDocument(...)`](#setdocument)
* [`getDocument(...)`](#getdocument)
* [`updateDocument(...)`](#updatedocument)
* [`deleteDocument(...)`](#deletedocument)
* [`getCollection(...)`](#getcollection)
* [`getCollectionGroup(...)`](#getcollectiongroup)
* [`enableNetwork()`](#enablenetwork)
* [`disableNetwork()`](#disablenetwork)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### addDocument(...)

```typescript
addDocument(options: AddDocumentOptions) => Promise<AddDocumentResult>
```

Adds a new document to a collection with the given data.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#adddocumentoptions">AddDocumentOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#adddocumentresult">AddDocumentResult</a>&gt;</code>

**Since:** 5.2.0

--------------------


### setDocument(...)

```typescript
setDocument(options: SetDocumentOptions) => Promise<void>
```

Writes to the document referred to by the specified reference.
If the document does not yet exist, it will be created.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#setdocumentoptions">SetDocumentOptions</a></code> |

**Since:** 5.2.0

--------------------


### getDocument(...)

```typescript
getDocument<T extends DocumentData = DocumentData>(options: GetDocumentOptions) => Promise<GetDocumentResult<T>>
```

Reads the document referred to by the specified reference.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#getdocumentoptions">GetDocumentOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getdocumentresult">GetDocumentResult</a>&lt;T&gt;&gt;</code>

**Since:** 5.2.0

--------------------


### updateDocument(...)

```typescript
updateDocument(options: UpdateDocumentOptions) => Promise<void>
```

Updates fields in the document referred to by the specified reference.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#updatedocumentoptions">UpdateDocumentOptions</a></code> |

**Since:** 5.2.0

--------------------


### deleteDocument(...)

```typescript
deleteDocument(options: DeleteDocumentOptions) => Promise<void>
```

Deletes the document referred to by the specified reference.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletedocumentoptions">DeleteDocumentOptions</a></code> |

**Since:** 5.2.0

--------------------


### getCollection(...)

```typescript
getCollection<T extends DocumentData = DocumentData>(options: GetCollectionOptions) => Promise<GetCollectionResult<T>>
```

Reads the collection referenced by the specified reference.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#getcollectionoptions">GetCollectionOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcollectionresult">GetCollectionResult</a>&lt;T&gt;&gt;</code>

**Since:** 5.2.0

--------------------


### getCollectionGroup(...)

```typescript
getCollectionGroup<T extends DocumentData = DocumentData>(options: GetCollectionGroupOptions) => Promise<GetCollectionGroupResult<T>>
```

Reads the collection group referenced by the specified reference.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getcollectiongroupoptions">GetCollectionGroupOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcollectiongroupresult">GetCollectionGroupResult</a>&lt;T&gt;&gt;</code>

--------------------


### enableNetwork()

```typescript
enableNetwork() => Promise<void>
```

Re-enables use of the network.

**Since:** 5.2.0

--------------------


### disableNetwork()

```typescript
disableNetwork() => Promise<void>
```

Disables use of the network.

**Since:** 5.2.0

--------------------


### Interfaces


#### AddDocumentResult

| Prop            | Type                                                            | Description                                | Since |
| --------------- | --------------------------------------------------------------- | ------------------------------------------ | ----- |
| **`reference`** | <code><a href="#documentreference">DocumentReference</a></code> | The reference of the newly added document. | 5.2.0 |


#### DocumentReference

| Prop       | Type                | Description                                      | Since |
| ---------- | ------------------- | ------------------------------------------------ | ----- |
| **`id`**   | <code>string</code> | The document's identifier within its collection. | 5.2.0 |
| **`path`** | <code>string</code> | The path of the document.                        | 5.2.0 |


#### AddDocumentOptions

| Prop            | Type                                                  | Description                                                                         | Since |
| --------------- | ----------------------------------------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`reference`** | <code>string</code>                                   | The reference as a string, with path components separated by a forward slash (`/`). | 5.2.0 |
| **`data`**      | <code><a href="#documentdata">DocumentData</a></code> | An object containing the data for the new document.                                 | 5.2.0 |


#### DocumentData


#### SetDocumentOptions

| Prop            | Type                                                  | Description                                                                         | Since |
| --------------- | ----------------------------------------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`reference`** | <code>string</code>                                   | The reference as a string, with path components separated by a forward slash (`/`). | 5.2.0 |
| **`data`**      | <code><a href="#documentdata">DocumentData</a></code> | An object containing the data for the new document.                                 | 5.2.0 |
| **`merge`**     | <code>boolean</code>                                  | Whether to merge the provided data with an existing document.                       | 5.2.0 |


#### GetDocumentResult

| Prop           | Type                                                                   | Description                    | Since |
| -------------- | ---------------------------------------------------------------------- | ------------------------------ | ----- |
| **`snapshot`** | <code><a href="#documentsnapshot">DocumentSnapshot</a>&lt;T&gt;</code> | The current document contents. | 5.2.0 |


#### DocumentSnapshot

| Prop       | Type                | Description                                                                                        | Since |
| ---------- | ------------------- | -------------------------------------------------------------------------------------------------- | ----- |
| **`id`**   | <code>string</code> | The document's identifier within its collection.                                                   | 5.2.0 |
| **`path`** | <code>string</code> | The path of the document.                                                                          | 5.2.0 |
| **`data`** | <code>T</code>      | An object containing the data for the document. Returns `undefined` if the document doesn't exist. | 5.2.0 |


#### GetDocumentOptions

| Prop            | Type                | Description                                                                         | Since |
| --------------- | ------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`reference`** | <code>string</code> | The reference as a string, with path components separated by a forward slash (`/`). | 5.2.0 |


#### UpdateDocumentOptions

| Prop            | Type                                                  | Description                                                                         | Since |
| --------------- | ----------------------------------------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`reference`** | <code>string</code>                                   | The reference as a string, with path components separated by a forward slash (`/`). | 5.2.0 |
| **`data`**      | <code><a href="#documentdata">DocumentData</a></code> | An object containing the data for the new document.                                 | 5.2.0 |


#### DeleteDocumentOptions

| Prop            | Type                | Description                                                                         | Since |
| --------------- | ------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`reference`** | <code>string</code> | The reference as a string, with path components separated by a forward slash (`/`). | 5.2.0 |


#### GetCollectionResult

| Prop            | Type                                                                               | Description                      | Since |
| --------------- | ---------------------------------------------------------------------------------- | -------------------------------- | ----- |
| **`snapshots`** | <code><a href="#querydocumentsnapshot">QueryDocumentSnapshot</a>&lt;T&gt;[]</code> | The documents in the collection. | 5.2.0 |


#### QueryDocumentSnapshot

| Prop       | Type                | Description                                                                                        | Since |
| ---------- | ------------------- | -------------------------------------------------------------------------------------------------- | ----- |
| **`id`**   | <code>string</code> | The document's identifier within its collection.                                                   | 5.2.0 |
| **`path`** | <code>string</code> | The path of the document.                                                                          | 5.2.0 |
| **`data`** | <code>T</code>      | An object containing the data for the document. Returns `undefined` if the document doesn't exist. | 5.2.0 |


#### GetCollectionOptions

| Prop                   | Type                                                                                      | Description                                                                                         | Since |
| ---------------------- | ----------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`reference`**        | <code>string</code>                                                                       | The reference as a string, with path components separated by a forward slash (`/`).                 | 5.2.0 |
| **`compositeFilter`**  | <code><a href="#querycompositefilterconstraint">QueryCompositeFilterConstraint</a></code> | The filter to apply.                                                                                | 5.2.0 |
| **`queryConstraints`** | <code>QueryNonFilterConstraint[]</code>                                                   | Narrow or order the set of documents to retrieve, but do not explicitly filter for document fields. | 5.2.0 |


#### QueryCompositeFilterConstraint

| Prop                   | Type                                 | Description           | Since |
| ---------------------- | ------------------------------------ | --------------------- | ----- |
| **`type`**             | <code>'and' \| 'or'</code>           |                       |       |
| **`queryConstraints`** | <code>QueryFilterConstraint[]</code> | The filters to apply. | 5.2.0 |


#### QueryFieldFilterConstraint

| Prop            | Type                                                    | Description                    | Since |
| --------------- | ------------------------------------------------------- | ------------------------------ | ----- |
| **`type`**      | <code>'where'</code>                                    | The type of the constraint.    | 5.2.0 |
| **`fieldPath`** | <code>string</code>                                     | The path to compare.           | 5.2.0 |
| **`opStr`**     | <code><a href="#queryoperator">QueryOperator</a></code> | The operation string to apply. | 5.2.0 |
| **`value`**     | <code>unknown</code>                                    | The value for comparison.      | 5.2.0 |


#### QueryOrderByConstraint

| Prop               | Type                                                          | Description                 | Since |
| ------------------ | ------------------------------------------------------------- | --------------------------- | ----- |
| **`type`**         | <code>'orderBy'</code>                                        | The type of the constraint. | 5.2.0 |
| **`fieldPath`**    | <code>string</code>                                           | The path to compare.        | 5.2.0 |
| **`directionStr`** | <code><a href="#orderbydirection">OrderByDirection</a></code> | The direction to sort by.   | 5.2.0 |


#### QueryLimitConstraint

| Prop        | Type                                  | Description                            | Since |
| ----------- | ------------------------------------- | -------------------------------------- | ----- |
| **`type`**  | <code>'limit' \| 'limitToLast'</code> | The type of the constraint.            | 5.2.0 |
| **`limit`** | <code>number</code>                   | The maximum number of items to return. | 5.2.0 |


#### GetCollectionGroupResult

| Prop            | Type                                                                               | Description                      | Since |
| --------------- | ---------------------------------------------------------------------------------- | -------------------------------- | ----- |
| **`snapshots`** | <code><a href="#querydocumentsnapshot">QueryDocumentSnapshot</a>&lt;T&gt;[]</code> | The documents in the collection. | 5.2.0 |


#### GetCollectionGroupOptions

| Prop                   | Type                                          | Description                                                                                         | Since |
| ---------------------- | --------------------------------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`reference`**        | <code>string</code>                           | The reference as a string, with path components separated by a forward slash (`/`).                 | 5.2.0 |
| **`compositeFilters`** | <code>QueryCompositeFilterConstraint[]</code> | The filter to apply.                                                                                | 5.2.0 |
| **`queryConstraints`** | <code>QueryNonFilterConstraint[]</code>       | Narrow or order the set of documents to retrieve, but do not explicitly filter for document fields. | 5.2.0 |


### Type Aliases


#### QueryFilterConstraint

<code><a href="#queryfieldfilterconstraint">QueryFieldFilterConstraint</a> | <a href="#querycompositefilterconstraint">QueryCompositeFilterConstraint</a></code>


#### QueryOperator

<code>'&lt;' | '&lt;=' | '==' | '&gt;=' | '&gt;' | '!=' | 'array-contains' | 'array-contains-any' | 'in' | 'not-in'</code>


#### QueryNonFilterConstraint

<code><a href="#queryorderbyconstraint">QueryOrderByConstraint</a> | <a href="#querylimitconstraint">QueryLimitConstraint</a></code>


#### OrderByDirection

<code>'desc' | 'asc'</code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/crashlytics/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/crashlytics/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
