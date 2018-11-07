import { TestBed } from '@angular/core/testing';

import { BddServiceService } from './bdd-service.service';

describe('BddServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BddServiceService = TestBed.get(BddServiceService);
    expect(service).toBeTruthy();
  });
});
