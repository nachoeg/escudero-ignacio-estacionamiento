import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddLicensePlateComponent } from './add-license-plate.component';

describe('AddLicensePlateComponent', () => {
  let component: AddLicensePlateComponent;
  let fixture: ComponentFixture<AddLicensePlateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddLicensePlateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddLicensePlateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
