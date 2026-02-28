interface FieldValueMarker {
  __type__: string;
  [key: string]: any;
}

export class FieldValue {
  private readonly marker: FieldValueMarker;

  private constructor(marker: FieldValueMarker) {
    this.marker = marker;
  }

  static serverTimestamp(): FieldValue {
    return new FieldValue({ __type__: 'serverTimestamp' });
  }

  static arrayUnion(...elements: any[]): FieldValue {
    return new FieldValue({ __type__: 'arrayUnion', elements });
  }

  static arrayRemove(...elements: any[]): FieldValue {
    return new FieldValue({ __type__: 'arrayRemove', elements });
  }

  static delete(): FieldValue {
    return new FieldValue({ __type__: 'delete' });
  }

  static increment(operand: number): FieldValue {
    return new FieldValue({ __type__: 'increment', operand });
  }

  toJSON(): FieldValueMarker {
    return { ...this.marker };
  }
}
