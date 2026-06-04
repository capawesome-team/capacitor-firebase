export class DocumentReference {
  readonly id: string;
  readonly path: string;

  private constructor(path: string) {
    DocumentReference.validatePath(path);
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

  private static validatePath(path: string): void {
    const segments = path.split('/');
    const hasEmptySegment = segments.some(segment => segment.length === 0);
    if (segments.length % 2 !== 0 || hasEmptySegment) {
      throw new Error(
        `Invalid document reference. Document references must point to a document with an even number of non-empty path segments, but got '${path}'.`,
      );
    }
  }
}
