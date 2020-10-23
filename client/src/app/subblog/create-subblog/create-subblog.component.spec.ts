import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSubblogComponent } from './create-subblog.component';

describe('CreateSubblogComponent', () => {
  let component: CreateSubblogComponent;
  let fixture: ComponentFixture<CreateSubblogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateSubblogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSubblogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
