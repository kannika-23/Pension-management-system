import { TestBed } from '@angular/core/testing';

import { ServicespmpService } from './servicespmp.service';

describe('ServicespmpService', () => {
  let service: ServicespmpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicespmpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
