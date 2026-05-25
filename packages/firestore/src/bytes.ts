export class Bytes {
  private readonly base64: string;

  private constructor(base64: string) {
    this.base64 = base64;
  }

  static fromBase64String(base64: string): Bytes {
    return new Bytes(base64);
  }

  static fromUint8Array(array: Uint8Array): Bytes {
    const chunkSize = 0x8000;
    const chunks: string[] = [];
    for (let i = 0; i < array.byteLength; i += chunkSize) {
      const chunk = array.subarray(i, i + chunkSize);
      chunks.push(
        String.fromCharCode.apply(null, chunk as unknown as number[]),
      );
    }
    return new Bytes(btoa(chunks.join('')));
  }

  toBase64(): string {
    return this.base64;
  }

  toUint8Array(): Uint8Array {
    const binary = atob(this.base64);
    const array = new Uint8Array(binary.length);
    for (let i = 0; i < binary.length; i++) {
      array[i] = binary.charCodeAt(i);
    }
    return array;
  }

  toJSON(): { __type__: 'bytes'; bytes: string } {
    return {
      __type__: 'bytes',
      bytes: this.base64,
    };
  }
}
