# @capgo/capacitor-firebase-firestore
 <a href="https://capgo.app/"><img src='https://raw.githubusercontent.com/Cap-go/capgo/main/assets/capgo_banner.png' alt='Capgo - Instant updates for capacitor'/></a>

<div align="center">
  <h2><a href="https://capgo.app/?ref=plugin"> ➡️ Get Instant updates for your App with Capgo</a></h2>
  <h2><a href="https://capgo.app/consulting/?ref=plugin"> Missing a feature? We'll build the plugin for you 💪</a></h2>
</div>

Unofficial Capacitor plugin for [Firebase Cloud Firestore](https://firebase.google.com/docs/firestore/).[^1]


## Guides

- [Announcing the Capacitor Firebase Cloud Firestore Plugin](https://capgo.app/blog/announcing-the-capacitor-firebase-cloud-firestore-plugin/)

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |

## Installation

```bash
npm install @capgo/capacitor-firebase-firestore
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://github.com/cap-go/capacitor-firebase/blob/main/docs/firebase-setup.md#android) / [iOS](https://github.com/cap-go/capacitor-firebase/blob/main/docs/firebase-setup.md#ios) / [Web](https://github.com/cap-go/capacitor-firebase/blob/main/docs/firebase-setup.md#web)).

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$firebaseFirestoreVersion` version of `com.google.firebase:firebase-firestore` (default: `26.0.2`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Starter templates

The following starter templates are available:

- [Ionstarter Angular Firebase](https://ionstarter.dev/)

## Usage

```typescript
import { FirebaseFirestore } from '@capgo/capacitor-firebase-firestore';

const addDocument = async () => {
  await FirebaseFirestore.addDocument({
    reference: 'users',
    data: { 
      first: 'Alan', 
      last: 'Turing', 
      born: 1912 
    },
  });
};

const setDocument = async () => {
  await FirebaseFirestore.setDocument({
    reference: 'users/Aorq09lkt1ynbR7xhTUx',
    data: { 
      first: 'Alan', 
      last: 'Turing', 
      born: 1912 
    },
    merge: true,
  });
};

const getDocument = async () => {
  const { snapshot } = await FirebaseFirestore.getDocument({
    reference: 'users/Aorq09lkt1ynbR7xhTUx',
  });
  return snapshot;
};

const updateDocument = async () => {
  await FirebaseFirestore.updateDocument({
    reference: 'users/Aorq09lkt1ynbR7xhTUx',
    data: { 
      first: 'Alan', 
      last: 'Turing', 
      born: 1912 
    },
  });
};

const deleteDocument = async () => {
  await FirebaseFirestore.deleteDocument({
    reference: 'users/Aorq09lkt1ynbR7xhTUx',
  });
};

const writeBatch = async () => {
  await FirebaseFirestore.writeBatch({
    operations: [
      {
        type: 'set',
        reference: 'users/Aorq09lkt1ynbR7xhTUx',
        data: { 
          first: 'Alan', 
          last: 'Turing', 
          born: 1912 
        },
        options: { merge: true },
      },
      {
        type: 'update',
        reference: 'users/Aorq09lkt1ynbR7xhTUx',
        data: { 
          first: 'Alan', 
          last: 'Turing', 
          born: 1912 
        },
      },
      {
        type: 'delete',
        reference: 'users/Aorq09lkt1ynbR7xhTUx',
      },
    ],
  });
};

const getCollection = async () => {
  const { snapshots } = await FirebaseFirestore.getCollection({
    reference: 'users',
    compositeFilter: {
      type: 'and',
      queryConstraints: [
        {
          type: 'where',
          fieldPath: 'born',
          opStr: '==',
          value: 1912,
        },
      ],
    },
    queryConstraints: [
      {
        type: 'orderBy',
        fieldPath: 'born',
        directionStr: 'desc',
      },
      {
        type: 'limit',
        limit: 10,
      },
    ],
  });
  return snapshots;
};

const getCollectionGroup = async () => {
  const { snapshots } = await FirebaseFirestore.getCollectionGroup({
    reference: 'users',
    compositeFilter: {
      type: 'and',
      queryConstraints: [
        {
          type: 'where',
          fieldPath: 'born',
          opStr: '==',
          value: 1912,
        },
      ],
    },
    queryConstraints: [
      {
        type: 'orderBy',
        fieldPath: 'born',
        directionStr: 'desc',
      },
      {
        type: 'limit',
        limit: 10,
      },
    ],
  });
  return snapshots;
};

const enableNetwork = async () => {
  await FirebaseFirestore.enableNetwork();
};

const disableNetwork = async () => {
  await FirebaseFirestore.disableNetwork();
};

const useEmulator = async () => {
  await FirebaseFirestore.useEmulator({
    host: '10.0.2.2',
    port: 9001,
  });
};

const addDocumentSnapshotListener = async () => {
  const callbackId = await FirebaseFirestore.addDocumentSnapshotListener(
    {
      reference: 'users/Aorq09lkt1ynbR7xhTUx',
    },
    (event, error) => {
      if (error) {
        console.error(error);
      } else {
        console.log(event);
      }
    }
  );
  return callbackId;
};

const addCollectionSnapshotListener = async () => {
  const callbackId = await FirebaseFirestore.addCollectionSnapshotListener(
    {
      reference: 'users',
      compositeFilter: {
        type: 'and',
        queryConstraints: [
          {
            type: 'where',
            fieldPath: 'born',
            opStr: '==',
            value: 1912,
          },
        ],
      },
      queryConstraints: [
        {
          type: 'orderBy',
          fieldPath: 'born',
          directionStr: 'desc',
        },
        {
          type: 'limit',
          limit: 10,
        },
      ],
    },
    (event, error) => {
      if (error) {
        console.error(error);
      } else {
        console.log(event);
      }
    }
  );
  return callbackId;
};

const addCollectionGroupSnapshotListener = async () => {
  const callbackId = await FirebaseFirestore.addCollectionGroupSnapshotListener(
    {
      reference: 'users',
      compositeFilter: {
        type: 'and',
        queryConstraints: [
          {
            type: 'where',
            fieldPath: 'born',
            opStr: '==',
            value: 1912,
          },
        ],
      },
      queryConstraints: [
        {
          type: 'orderBy',
          fieldPath: 'born',
          directionStr: 'desc',
        },
        {
          type: 'limit',
          limit: 10,
        },
      ],
    },
    (event, error) => {
      if (error) {
        console.error(error);
      } else {
        console.log(event);
      }
    }
  );
  return callbackId;
};

const removeSnapshotListener = async (callbackId: string) => {
  await FirebaseFirestore.removeSnapshotListener({
    callbackId,
  });
};

const removeAllListeners = async () => {
  await FirebaseFirestore.removeAllListeners();
};
```

## API

<docgen-index>

* [`addDocument(...)`](#adddocument)
* [`setDocument(...)`](#setdocument)
* [`getDocument(...)`](#getdocument)
* [`updateDocument(...)`](#updatedocument)
* [`deleteDocument(...)`](#deletedocument)
* [`writeBatch(...)`](#writebatch)
* [`getCollection(...)`](#getcollection)
* [`getCollectionGroup(...)`](#getcollectiongroup)
* [`getCountFromServer(...)`](#getcountfromserver)
* [`clearPersistence()`](#clearpersistence)
* [`enableNetwork()`](#enablenetwork)
* [`disableNetwork()`](#disablenetwork)
* [`useEmulator(...)`](#useemulator)
* [`addDocumentSnapshotListener(...)`](#adddocumentsnapshotlistener)
* [`addCollectionSnapshotListener(...)`](#addcollectionsnapshotlistener)
* [`addCollectionGroupSnapshotListener(...)`](#addcollectiongroupsnapshotlistener)
* [`removeSnapshotListener(...)`](#removesnapshotlistener)
* [`removeAllListeners()`](#removealllisteners)
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


### writeBatch(...)

```typescript
writeBatch(options: WriteBatchOptions) => Promise<void>
```

Execute multiple write operations as a single batch.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#writebatchoptions">WriteBatchOptions</a></code> |

**Since:** 6.1.0

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


### getCountFromServer(...)

```typescript
getCountFromServer(options: GetCountFromServerOptions) => Promise<GetCountFromServerResult>
```

Fetches the number of documents in a collection.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getcountfromserveroptions">GetCountFromServerOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcountfromserverresult">GetCountFromServerResult</a>&gt;</code>

**Since:** 6.4.0

--------------------


### clearPersistence()

```typescript
clearPersistence() => Promise<void>
```

Clears the persistent storage. This includes pending writes and cached documents.

Must be called after the app is shutdown or when the app is first initialized.

**Since:** 5.2.0

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


### useEmulator(...)

```typescript
useEmulator(options: UseEmulatorOptions) => Promise<void>
```

Instrument your app to talk to the Firestore emulator.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#useemulatoroptions">UseEmulatorOptions</a></code> |

**Since:** 6.1.0

--------------------


### addDocumentSnapshotListener(...)

```typescript
addDocumentSnapshotListener<T extends DocumentData = DocumentData>(options: AddDocumentSnapshotListenerOptions, callback: AddDocumentSnapshotListenerCallback<T>) => Promise<CallbackId>
```

Adds a listener for document snapshot events.

| Param          | Type                                                                                                         |
| -------------- | ------------------------------------------------------------------------------------------------------------ |
| **`options`**  | <code><a href="#adddocumentsnapshotlisteneroptions">AddDocumentSnapshotListenerOptions</a></code>            |
| **`callback`** | <code><a href="#adddocumentsnapshotlistenercallback">AddDocumentSnapshotListenerCallback</a>&lt;T&gt;</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

**Since:** 5.2.0

--------------------


### addCollectionSnapshotListener(...)

```typescript
addCollectionSnapshotListener<T extends DocumentData = DocumentData>(options: AddCollectionSnapshotListenerOptions, callback: AddCollectionSnapshotListenerCallback<T>) => Promise<CallbackId>
```

Adds a listener for collection snapshot events.

| Param          | Type                                                                                                             |
| -------------- | ---------------------------------------------------------------------------------------------------------------- |
| **`options`**  | <code><a href="#addcollectionsnapshotlisteneroptions">AddCollectionSnapshotListenerOptions</a></code>            |
| **`callback`** | <code><a href="#addcollectionsnapshotlistenercallback">AddCollectionSnapshotListenerCallback</a>&lt;T&gt;</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

**Since:** 5.2.0

--------------------


### addCollectionGroupSnapshotListener(...)

```typescript
addCollectionGroupSnapshotListener<T extends DocumentData = DocumentData>(options: AddCollectionGroupSnapshotListenerOptions, callback: AddCollectionGroupSnapshotListenerCallback<T>) => Promise<CallbackId>
```

Adds a listener for collection group snapshot events.

| Param          | Type                                                                                                                       |
| -------------- | -------------------------------------------------------------------------------------------------------------------------- |
| **`options`**  | <code><a href="#addcollectiongroupsnapshotlisteneroptions">AddCollectionGroupSnapshotListenerOptions</a></code>            |
| **`callback`** | <code><a href="#addcollectiongroupsnapshotlistenercallback">AddCollectionGroupSnapshotListenerCallback</a>&lt;T&gt;</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

**Since:** 6.1.0

--------------------


### removeSnapshotListener(...)

```typescript
removeSnapshotListener(options: RemoveSnapshotListenerOptions) => Promise<void>
```

Remove a listener for document or collection snapshot events.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removesnapshotlisteneroptions">RemoveSnapshotListenerOptions</a></code> |

**Since:** 5.2.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

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

| Prop            | Type                                                  | Description                                                                         | Default            | Since |
| --------------- | ----------------------------------------------------- | ----------------------------------------------------------------------------------- | ------------------ | ----- |
| **`reference`** | <code>string</code>                                   | The reference as a string, with path components separated by a forward slash (`/`). |                    | 5.2.0 |
| **`data`**      | <code><a href="#documentdata">DocumentData</a></code> | An object containing the data for the new document.                                 |                    | 5.2.0 |
| **`merge`**     | <code>boolean</code>                                  | Whether to merge the provided data with an existing document.                       | <code>false</code> | 5.2.0 |


#### GetDocumentResult

| Prop           | Type                                                                   | Description                    | Since |
| -------------- | ---------------------------------------------------------------------- | ------------------------------ | ----- |
| **`snapshot`** | <code><a href="#documentsnapshot">DocumentSnapshot</a>&lt;T&gt;</code> | The current document contents. | 5.2.0 |


#### DocumentSnapshot

| Prop           | Type                                                          | Description                                                                                   | Since |
| -------------- | ------------------------------------------------------------- | --------------------------------------------------------------------------------------------- | ----- |
| **`id`**       | <code>string</code>                                           | The document's identifier within its collection.                                              | 5.2.0 |
| **`path`**     | <code>string</code>                                           | The path of the document.                                                                     | 5.2.0 |
| **`data`**     | <code>T \| null</code>                                        | An object containing the data for the document. Returns `null` if the document doesn't exist. | 5.2.0 |
| **`metadata`** | <code><a href="#snapshotmetadata">SnapshotMetadata</a></code> | Metadata about the snapshot, concerning its source and if it has local modifications.         | 6.2.0 |


#### SnapshotMetadata

| Prop                   | Type                 | Description                                               | Since |
| ---------------------- | -------------------- | --------------------------------------------------------- | ----- |
| **`fromCache`**        | <code>boolean</code> | True if the snapshot was created from cached data.        | 6.2.0 |
| **`hasPendingWrites`** | <code>boolean</code> | True if the snapshot was created from pending write data. | 6.2.0 |


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


#### WriteBatchOptions

| Prop             | Type                               | Description                             | Since |
| ---------------- | ---------------------------------- | --------------------------------------- | ----- |
| **`operations`** | <code>WriteBatchOperation[]</code> | The operations to execute in the batch. | 6.1.0 |


#### WriteBatchOperation

| Prop            | Type                                                  | Description                                                                         | Since |
| --------------- | ----------------------------------------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`type`**      | <code>'set' \| 'update' \| 'delete'</code>            | The type of operation.                                                              | 6.1.0 |
| **`reference`** | <code>string</code>                                   | The reference as a string, with path components separated by a forward slash (`/`). | 6.1.0 |
| **`data`**      | <code><a href="#documentdata">DocumentData</a></code> | An object containing the data for the new document.                                 | 6.1.0 |
| **`options`**   | <code><a href="#setoptions">SetOptions</a></code>     | An object to configure the set behavior.                                            | 7.3.0 |


#### SetOptions

| Prop        | Type                 | Description                                                                | Default            | Since |
| ----------- | -------------------- | -------------------------------------------------------------------------- | ------------------ | ----- |
| **`merge`** | <code>boolean</code> | Whether a merge should be performed or the document should be overwritten. | <code>false</code> | 7.3.0 |


#### GetCollectionResult

| Prop            | Type                                                                     | Description                      | Since |
| --------------- | ------------------------------------------------------------------------ | -------------------------------- | ----- |
| **`snapshots`** | <code><a href="#documentsnapshot">DocumentSnapshot</a>&lt;T&gt;[]</code> | The documents in the collection. | 5.2.0 |


#### GetCollectionOptions

| Prop                   | Type                                                                                      | Description                                                                                         | Since |
| ---------------------- | ----------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`reference`**        | <code>string</code>                                                                       | The reference as a string, with path components separated by a forward slash (`/`).                 | 5.2.0 |
| **`compositeFilter`**  | <code><a href="#querycompositefilterconstraint">QueryCompositeFilterConstraint</a></code> | The filter to apply.                                                                                | 5.2.0 |
| **`queryConstraints`** | <code>QueryNonFilterConstraint[]</code>                                                   | Narrow or order the set of documents to retrieve, but do not explicitly filter for document fields. | 5.2.0 |


#### QueryCompositeFilterConstraint

| Prop                   | Type                                 | Description                 | Since |
| ---------------------- | ------------------------------------ | --------------------------- | ----- |
| **`type`**             | <code>'and' \| 'or'</code>           | The type of the constraint. | 5.2.0 |
| **`queryConstraints`** | <code>QueryFilterConstraint[]</code> | The filters to apply.       | 5.2.0 |


#### QueryFieldFilterConstraint

| Prop            | Type                                                    | Description                    | Since |
| --------------- | ------------------------------------------------------- | ------------------------------ | ----- |
| **`type`**      | <code>'where'</code>                                    | The type of the constraint.    | 5.2.0 |
| **`fieldPath`** | <code>string</code>                                     | The path to compare.           | 5.2.0 |
| **`opStr`**     | <code><a href="#queryoperator">QueryOperator</a></code> | The operation string to apply. | 5.2.0 |
| **`value`**     | <code>any</code>                                        | The value for comparison.      | 5.2.0 |


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


#### QueryStartAtConstraint

| Prop            | Type                                   | Description                                                                                                                                                        | Since |
| --------------- | -------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`type`**      | <code>'startAt' \| 'startAfter'</code> | The type of the constraint.                                                                                                                                        | 5.2.0 |
| **`reference`** | <code>string</code>                    | The reference to start at or after as a string, with path components separated by a forward slash (`/`). **Attention**: This requires an additional document read. | 5.2.0 |


#### QueryEndAtConstraint

| Prop            | Type                                | Description                                                                                                                                                          | Since |
| --------------- | ----------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`type`**      | <code>'endAt' \| 'endBefore'</code> | The type of the constraint.                                                                                                                                          | 5.2.0 |
| **`reference`** | <code>string</code>                 | The reference as to end at or before as a string, with path components separated by a forward slash (`/`). **Attention**: This requires an additional document read. | 5.2.0 |


#### GetCollectionGroupResult

| Prop            | Type                                                                     | Description                      | Since |
| --------------- | ------------------------------------------------------------------------ | -------------------------------- | ----- |
| **`snapshots`** | <code><a href="#documentsnapshot">DocumentSnapshot</a>&lt;T&gt;[]</code> | The documents in the collection. | 5.2.0 |


#### GetCollectionGroupOptions

| Prop                   | Type                                                                                      | Description                                                                                         | Since |
| ---------------------- | ----------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`reference`**        | <code>string</code>                                                                       | The reference as a string, with path components separated by a forward slash (`/`).                 | 5.2.0 |
| **`compositeFilter`**  | <code><a href="#querycompositefilterconstraint">QueryCompositeFilterConstraint</a></code> | The filter to apply.                                                                                | 5.2.0 |
| **`queryConstraints`** | <code>QueryNonFilterConstraint[]</code>                                                   | Narrow or order the set of documents to retrieve, but do not explicitly filter for document fields. | 5.2.0 |


#### GetCountFromServerResult

| Prop        | Type                | Description                                | Since |
| ----------- | ------------------- | ------------------------------------------ | ----- |
| **`count`** | <code>number</code> | The number of documents in the collection. | 6.4.0 |


#### GetCountFromServerOptions

| Prop            | Type                | Description                                                                         | Since |
| --------------- | ------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`reference`** | <code>string</code> | The reference as a string, with path components separated by a forward slash (`/`). | 6.4.0 |


#### UseEmulatorOptions

| Prop       | Type                | Description                                                                                                                                                                     | Default           | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`host`** | <code>string</code> | The emulator host without any port or scheme. Note when using a Android Emulator device: 10.0.2.2 is the special IP address to connect to the 'localhost' of the host computer. |                   | 6.1.0 |
| **`port`** | <code>number</code> | The emulator port.                                                                                                                                                              | <code>8080</code> | 6.1.0 |


#### AddDocumentSnapshotListenerOptions

| Prop            | Type                | Description                                                                         | Since |
| --------------- | ------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`reference`** | <code>string</code> | The reference as a string, with path components separated by a forward slash (`/`). | 5.2.0 |


#### AddCollectionSnapshotListenerOptions

| Prop                   | Type                                                                                      | Description                                                                                         | Since |
| ---------------------- | ----------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`reference`**        | <code>string</code>                                                                       | The reference as a string, with path components separated by a forward slash (`/`).                 | 5.2.0 |
| **`compositeFilter`**  | <code><a href="#querycompositefilterconstraint">QueryCompositeFilterConstraint</a></code> | The filter to apply.                                                                                | 5.2.0 |
| **`queryConstraints`** | <code>QueryNonFilterConstraint[]</code>                                                   | Narrow or order the set of documents to retrieve, but do not explicitly filter for document fields. | 5.2.0 |


#### AddCollectionGroupSnapshotListenerOptions

| Prop                   | Type                                                                                      | Description                                                                                         | Since |
| ---------------------- | ----------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`reference`**        | <code>string</code>                                                                       | The reference as a string, with path components separated by a forward slash (`/`).                 | 6.1.0 |
| **`compositeFilter`**  | <code><a href="#querycompositefilterconstraint">QueryCompositeFilterConstraint</a></code> | The filter to apply.                                                                                | 6.1.0 |
| **`queryConstraints`** | <code>QueryNonFilterConstraint[]</code>                                                   | Narrow or order the set of documents to retrieve, but do not explicitly filter for document fields. | 6.1.0 |


#### RemoveSnapshotListenerOptions

| Prop             | Type                                              | Since |
| ---------------- | ------------------------------------------------- | ----- |
| **`callbackId`** | <code><a href="#callbackid">CallbackId</a></code> | 5.2.0 |


### Type Aliases


#### QueryFilterConstraint

<code><a href="#queryfieldfilterconstraint">QueryFieldFilterConstraint</a> | <a href="#querycompositefilterconstraint">QueryCompositeFilterConstraint</a></code>


#### QueryOperator

<code>'&lt;' | '&lt;=' | '==' | '&gt;=' | '&gt;' | '!=' | 'array-contains' | 'array-contains-any' | 'in' | 'not-in'</code>


#### QueryNonFilterConstraint

<code><a href="#queryorderbyconstraint">QueryOrderByConstraint</a> | <a href="#querylimitconstraint">QueryLimitConstraint</a> | <a href="#querystartatconstraint">QueryStartAtConstraint</a> | <a href="#queryendatconstraint">QueryEndAtConstraint</a></code>


#### OrderByDirection

<code>'desc' | 'asc'</code>


#### AddDocumentSnapshotListenerCallback

<code>(event: <a href="#adddocumentsnapshotlistenercallbackevent">AddDocumentSnapshotListenerCallbackEvent</a>&lt;T&gt; | null, error: any): void</code>


#### AddDocumentSnapshotListenerCallbackEvent

<code><a href="#getdocumentresult">GetDocumentResult</a>&lt;T&gt;</code>


#### CallbackId

<code>string</code>


#### AddCollectionSnapshotListenerCallback

<code>(event: <a href="#addcollectionsnapshotlistenercallbackevent">AddCollectionSnapshotListenerCallbackEvent</a>&lt;T&gt; | null, error: any): void</code>


#### AddCollectionSnapshotListenerCallbackEvent

<code><a href="#getcollectionresult">GetCollectionResult</a>&lt;T&gt;</code>


#### AddCollectionGroupSnapshotListenerCallback

<code>(event: <a href="#addcollectiongroupsnapshotlistenercallbackevent">AddCollectionGroupSnapshotListenerCallbackEvent</a>&lt;T&gt; | null, error: any): void</code>


#### AddCollectionGroupSnapshotListenerCallbackEvent

<code><a href="#getcollectiongroupresult">GetCollectionGroupResult</a>&lt;T&gt;</code>

</docgen-api>

## Limitations

This plugin currently has the following limitations:

- The `Timestamp` data type is not yet supported (see https://github.com/cap-go/capacitor-firebase/issues/474). Use a `number` or a `string` instead.
- The `FieldValue` data type is not yet supported (see https://github.com/cap-go/capacitor-firebase/issues/443).

## Changelog

See [CHANGELOG.md](https://github.com/cap-go/capacitor-firebase/blob/main/packages/firestore/CHANGELOG.md).

## License

See [LICENSE](https://github.com/cap-go/capacitor-firebase/blob/main/packages/firestore/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
