export class Timestamp {
  readonly seconds: number;
  readonly nanoseconds: number;

  constructor(seconds: number, nanoseconds: number) {
    this.seconds = seconds;
    this.nanoseconds = nanoseconds;
  }

  static now(): Timestamp {
    return Timestamp.fromMillis(Date.now());
  }

  static fromDate(date: Date): Timestamp {
    const ms = date.getTime();
    const seconds = Math.floor(ms / 1000);
    const nanoseconds = (ms % 1000) * 1_000_000;
    return new Timestamp(seconds, nanoseconds);
  }

  static fromMillis(ms: number): Timestamp {
    const seconds = Math.floor(ms / 1000);
    const nanoseconds = (ms % 1000) * 1_000_000;
    return new Timestamp(seconds, nanoseconds);
  }

  toDate(): Date {
    return new Date(this.toMillis());
  }

  toMillis(): number {
    return this.seconds * 1000 + Math.floor(this.nanoseconds / 1_000_000);
  }

  toJSON(): { __type__: 'timestamp'; seconds: number; nanoseconds: number } {
    return {
      __type__: 'timestamp',
      seconds: this.seconds,
      nanoseconds: this.nanoseconds,
    };
  }
}
