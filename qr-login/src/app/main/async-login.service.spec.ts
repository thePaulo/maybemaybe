import { TestBed } from '@angular/core/testing';

import { AsyncLoginService } from './async-login.service';

describe('AsyncLoginService', () => {
  let service: AsyncLoginService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AsyncLoginService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
