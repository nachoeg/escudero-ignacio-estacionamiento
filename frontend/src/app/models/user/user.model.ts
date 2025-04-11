import { ParkingSession } from '../parking-session/parking-session.model';

export class User {
  id: number;
  email: string;
  phone: string;
  password: string;
  balance: number;
  licensePlates: string[];
  parkingSessionActive: boolean;
  parkingSession?: ParkingSession;

  constructor(
    id: number,
    email: string,
    phone: string,
    password: string,
    balance: number,
    licensePlates: string[],
    parkingSessionActive: boolean,
    parkingSession?: ParkingSession
  ) {
    this.id = id;
    this.email = email;
    this.phone = phone;
    this.password = password;
    this.balance = balance;
    this.licensePlates = licensePlates;
    this.parkingSessionActive = parkingSessionActive;
    this.parkingSession = parkingSession;
  }
}
