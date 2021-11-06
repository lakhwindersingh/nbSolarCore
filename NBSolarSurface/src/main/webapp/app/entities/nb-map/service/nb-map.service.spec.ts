import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INBMap, NBMap } from '../nb-map.model';

import { NBMapService } from './nb-map.service';

describe('NBMap Service', () => {
  let service: NBMapService;
  let httpMock: HttpTestingController;
  let elemDefault: INBMap;
  let expectedResult: INBMap | INBMap[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NBMapService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nbID: 'AAAAAAA',
      nbName: 'AAAAAAA',
      nbOwner: 'AAAAAAA',
      nbOwnerPrivateKey: 'AAAAAAA',
      nbOwnerPublicKey: 'AAAAAAA',
      nbMapPublishMethod: 'AAAAAAA',
      nbSubscriptionDate: 'AAAAAAA',
      nbSubscriptionLastDate: 'AAAAAAA',
      nbLastUpdated: 'AAAAAAA',
      nbLastUpdatedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a NBMap', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new NBMap()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NBMap', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nbID: 'BBBBBB',
          nbName: 'BBBBBB',
          nbOwner: 'BBBBBB',
          nbOwnerPrivateKey: 'BBBBBB',
          nbOwnerPublicKey: 'BBBBBB',
          nbMapPublishMethod: 'BBBBBB',
          nbSubscriptionDate: 'BBBBBB',
          nbSubscriptionLastDate: 'BBBBBB',
          nbLastUpdated: 'BBBBBB',
          nbLastUpdatedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NBMap', () => {
      const patchObject = Object.assign(
        {
          nbOwner: 'BBBBBB',
          nbMapPublishMethod: 'BBBBBB',
          nbSubscriptionDate: 'BBBBBB',
          nbLastUpdated: 'BBBBBB',
        },
        new NBMap()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NBMap', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nbID: 'BBBBBB',
          nbName: 'BBBBBB',
          nbOwner: 'BBBBBB',
          nbOwnerPrivateKey: 'BBBBBB',
          nbOwnerPublicKey: 'BBBBBB',
          nbMapPublishMethod: 'BBBBBB',
          nbSubscriptionDate: 'BBBBBB',
          nbSubscriptionLastDate: 'BBBBBB',
          nbLastUpdated: 'BBBBBB',
          nbLastUpdatedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a NBMap', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNBMapToCollectionIfMissing', () => {
      it('should add a NBMap to an empty array', () => {
        const nBMap: INBMap = { id: 123 };
        expectedResult = service.addNBMapToCollectionIfMissing([], nBMap);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nBMap);
      });

      it('should not add a NBMap to an array that contains it', () => {
        const nBMap: INBMap = { id: 123 };
        const nBMapCollection: INBMap[] = [
          {
            ...nBMap,
          },
          { id: 456 },
        ];
        expectedResult = service.addNBMapToCollectionIfMissing(nBMapCollection, nBMap);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NBMap to an array that doesn't contain it", () => {
        const nBMap: INBMap = { id: 123 };
        const nBMapCollection: INBMap[] = [{ id: 456 }];
        expectedResult = service.addNBMapToCollectionIfMissing(nBMapCollection, nBMap);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nBMap);
      });

      it('should add only unique NBMap to an array', () => {
        const nBMapArray: INBMap[] = [{ id: 123 }, { id: 456 }, { id: 94893 }];
        const nBMapCollection: INBMap[] = [{ id: 123 }];
        expectedResult = service.addNBMapToCollectionIfMissing(nBMapCollection, ...nBMapArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nBMap: INBMap = { id: 123 };
        const nBMap2: INBMap = { id: 456 };
        expectedResult = service.addNBMapToCollectionIfMissing([], nBMap, nBMap2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nBMap);
        expect(expectedResult).toContain(nBMap2);
      });

      it('should accept null and undefined values', () => {
        const nBMap: INBMap = { id: 123 };
        expectedResult = service.addNBMapToCollectionIfMissing([], null, nBMap, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nBMap);
      });

      it('should return initial array if no NBMap is added', () => {
        const nBMapCollection: INBMap[] = [{ id: 123 }];
        expectedResult = service.addNBMapToCollectionIfMissing(nBMapCollection, undefined, null);
        expect(expectedResult).toEqual(nBMapCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
