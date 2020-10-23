import { TestBed } from '@angular/core/testing';

import { SubblogService } from './subblog.service';

describe('SubblogService', () => {
  let service: SubblogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubblogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
