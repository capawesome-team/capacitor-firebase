import type {
  AddCollectionGroupSnapshotListenerCallback,
  AddCollectionGroupSnapshotListenerOptions,
  AddCollectionSnapshotListenerCallback,
  AddCollectionSnapshotListenerOptions,
  AddDocumentOptions,
  AddDocumentResult,
  AddDocumentSnapshotListenerCallback,
  AddDocumentSnapshotListenerOptions,
  CallbackId,
  DeleteDocumentOptions,
  DocumentData,
  FirebaseFirestorePlugin,
  GetCollectionGroupOptions,
  GetCollectionGroupResult,
  GetCollectionOptions,
  GetCollectionResult,
  GetCountFromServerOptions,
  GetCountFromServerResult,
  GetDocumentOptions,
  GetDocumentResult,
  RemoveSnapshotListenerOptions,
  SetDocumentOptions,
  UpdateDocumentOptions,
  UseEmulatorOptions,
  WriteBatchOptions,
} from './definitions';
import { deserializeData } from './utils';

export class FirebaseFirestoreClient implements FirebaseFirestorePlugin {
  private readonly plugin: FirebaseFirestorePlugin;

  constructor(plugin: FirebaseFirestorePlugin) {
    this.plugin = plugin;
  }

  async addDocument(options: AddDocumentOptions): Promise<AddDocumentResult> {
    return this.plugin.addDocument(options);
  }

  async setDocument(options: SetDocumentOptions): Promise<void> {
    return this.plugin.setDocument(options);
  }

  async getDocument<T extends DocumentData = DocumentData>(
    options: GetDocumentOptions,
  ): Promise<GetDocumentResult<T>> {
    const result = await this.plugin.getDocument<T>(options);
    if (result.snapshot.data !== null) {
      result.snapshot.data = deserializeData(result.snapshot.data);
    }
    return result;
  }

  async updateDocument(options: UpdateDocumentOptions): Promise<void> {
    return this.plugin.updateDocument(options);
  }

  async deleteDocument(options: DeleteDocumentOptions): Promise<void> {
    return this.plugin.deleteDocument(options);
  }

  async writeBatch(options: WriteBatchOptions): Promise<void> {
    return this.plugin.writeBatch(options);
  }

  async getCollection<T extends DocumentData = DocumentData>(
    options: GetCollectionOptions,
  ): Promise<GetCollectionResult<T>> {
    const result = await this.plugin.getCollection<T>(options);
    for (const snapshot of result.snapshots) {
      if (snapshot.data !== null) {
        snapshot.data = deserializeData(snapshot.data);
      }
    }
    return result;
  }

  async getCollectionGroup<T extends DocumentData = DocumentData>(
    options: GetCollectionGroupOptions,
  ): Promise<GetCollectionGroupResult<T>> {
    const result = await this.plugin.getCollectionGroup<T>(options);
    for (const snapshot of result.snapshots) {
      if (snapshot.data !== null) {
        snapshot.data = deserializeData(snapshot.data);
      }
    }
    return result;
  }

  async getCountFromServer(
    options: GetCountFromServerOptions,
  ): Promise<GetCountFromServerResult> {
    return this.plugin.getCountFromServer(options);
  }

  async clearPersistence(): Promise<void> {
    return this.plugin.clearPersistence();
  }

  async enableNetwork(): Promise<void> {
    return this.plugin.enableNetwork();
  }

  async disableNetwork(): Promise<void> {
    return this.plugin.disableNetwork();
  }

  async useEmulator(options: UseEmulatorOptions): Promise<void> {
    return this.plugin.useEmulator(options);
  }

  async addDocumentSnapshotListener<T extends DocumentData = DocumentData>(
    options: AddDocumentSnapshotListenerOptions,
    callback: AddDocumentSnapshotListenerCallback<T>,
  ): Promise<CallbackId> {
    return this.plugin.addDocumentSnapshotListener<T>(
      options,
      (event, error) => {
        if (
          event?.snapshot.data !== null &&
          event?.snapshot.data !== undefined
        ) {
          event.snapshot.data = deserializeData(event.snapshot.data);
        }
        callback(event, error);
      },
    );
  }

  async addCollectionSnapshotListener<T extends DocumentData = DocumentData>(
    options: AddCollectionSnapshotListenerOptions,
    callback: AddCollectionSnapshotListenerCallback<T>,
  ): Promise<CallbackId> {
    return this.plugin.addCollectionSnapshotListener<T>(
      options,
      (event, error) => {
        if (event) {
          for (const snapshot of event.snapshots) {
            if (snapshot.data !== null) {
              snapshot.data = deserializeData(snapshot.data);
            }
          }
        }
        callback(event, error);
      },
    );
  }

  async addCollectionGroupSnapshotListener<
    T extends DocumentData = DocumentData,
  >(
    options: AddCollectionGroupSnapshotListenerOptions,
    callback: AddCollectionGroupSnapshotListenerCallback<T>,
  ): Promise<CallbackId> {
    return this.plugin.addCollectionGroupSnapshotListener<T>(
      options,
      (event, error) => {
        if (event) {
          for (const snapshot of event.snapshots) {
            if (snapshot.data !== null) {
              snapshot.data = deserializeData(snapshot.data);
            }
          }
        }
        callback(event, error);
      },
    );
  }

  async removeSnapshotListener(
    options: RemoveSnapshotListenerOptions,
  ): Promise<void> {
    return this.plugin.removeSnapshotListener(options);
  }

  async removeAllListeners(): Promise<void> {
    return this.plugin.removeAllListeners();
  }
}
