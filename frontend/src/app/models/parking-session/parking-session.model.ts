export class ParkingSession {
  id: number;
  startTime: Date;
  endTime?: Date;
  licensePlate: string;

  constructor(
    id: number,
    startTime: Date,
    endTime: Date | undefined,
    licensePlate: string
  ) {
    this.id = id;
    this.startTime = startTime;
    this.endTime = endTime;
    this.licensePlate = licensePlate;
  }
}
