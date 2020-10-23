import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubblogSideBarComponent } from './subblog-side-bar.component';

describe('SubblogSideBarComponent', () => {
  let component: SubblogSideBarComponent;
  let fixture: ComponentFixture<SubblogSideBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubblogSideBarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubblogSideBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
