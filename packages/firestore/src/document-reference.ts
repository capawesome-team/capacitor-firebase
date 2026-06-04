export class DocumentReference {
  readonly id: string;
  readonly path: string;

  private constructor(path: string) {
    this.path = path;
    this.id = path.substring(path.lastIndexOf('/') + 1);
  }

  static fromPath(path: string): DocumentReference {
    return new DocumentReference(path);
  }

  toJSON(): { __type__: 'documentReference'; id: string; path: string } {
    return {
      __type__: 'documentReference',
      id: this.id,
      path: this.path,
    };
  }
}
