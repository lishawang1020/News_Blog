import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListSubblogsComponent } from './list-subblogs.component';

describe('ListSubblogsComponent', () => {
  let component: ListSubblogsComponent;
  let fixture: ComponentFixture<ListSubblogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListSubblogsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListSubblogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
