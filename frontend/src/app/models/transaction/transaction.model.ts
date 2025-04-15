export class Transaction {
  id: number;
  amount: number;
  timestamp: Date;
  description: string;

  constructor(
    id: number,
    amount: number,
    timestamp: Date,
    description: string
  ) {
    this.id = id;
    this.amount = amount;
    this.timestamp = timestamp;
    this.description = description;
  }
}
