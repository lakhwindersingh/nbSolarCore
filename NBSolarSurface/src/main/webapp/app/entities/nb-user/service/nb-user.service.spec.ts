import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INBUser, NBUser } from '../nb-user.model';

import { NBUserService } from './nb-user.service';

describe('NBUser Service', () => {
  let service: NBUserService;
  let httpMock: HttpTestingController;
  let elemDefault: INBUser;
  let expectedResult: INBUser | INBUser[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NBUserService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nbUserID: 'AAAAAAA',
      nbAuthType: 'AAAAAAA',
      nbPasswordHash: 'AAAAAAA',
      nbFirstName: 'AAAAAAA',
      nbLastName: 'AAAAAAA',
      nbAddress: 'AAAAAAA',
      nbEmailId: 'AAAAAAA',
      nbPhone: 'AAAAAAA',
      nbIsActive: 'AAAAAAA',
      nbIsSuspended: 'AAAAAAA',
      nbIsBanished: 'AAAAAAA',
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

    it('should create a NBUser', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new NBUser()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NBUser', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nbUserID: 'BBBBBB',
          nbAuthType: 'BBBBBB',
          nbPasswordHash: 'BBBBBB',
          nbFirstName: 'BBBBBB',
          nbLastName: 'BBBBBB',
          nbAddress: 'BBBBBB',
          nbEmailId: 'BBBBBB',
          nbPhone: 'BBBBBB',
          nbIsActive: 'BBBBBB',
          nbIsSuspended: 'BBBBBB',
          nbIsBanished: 'BBBBBB',
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

    it('should partial update a NBUser', () => {
      const patchObject = Object.assign(
        {
          nbAuthType: 'BBBBBB',
          nbFirstName: 'BBBBBB',
          nbIsSuspended: 'BBBBBB',
        },
        new NBUser()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NBUser', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nbUserID: 'BBBBBB',
          nbAuthType: 'BBBBBB',
          nbPasswordHash: 'BBBBBB',
          nbFirstName: 'BBBBBB',
          nbLastName: 'BBBBBB',
          nbAddress: 'BBBBBB',
          nbEmailId: 'BBBBBB',
          nbPhone: 'BBBBBB',
          nbIsActive: 'BBBBBB',
          nbIsSuspended: 'BBBBBB',
          nbIsBanished: 'BBBBBB',
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

    it('should delete a NBUser', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNBUserToCollectionIfMissing', () => {
      it('should add a NBUser to an empty array', () => {
        const nBUser: INBUser = { id: 123 };
        expectedResult = service.addNBUserToCollectionIfMissing([], nBUser);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nBUser);
      });

      it('should not add a NBUser to an array that contains it', () => {
        const nBUser: INBUser = { id: 123 };
        const nBUserCollection: INBUser[] = [
          {
            ...nBUser,
          },
          { id: 456 },
        ];
        expectedResult = service.addNBUserToCollectionIfMissing(nBUserCollection, nBUser);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NBUser to an array that doesn't contain it", () => {
        const nBUser: INBUser = { id: 123 };
        const nBUserCollection: INBUser[] = [{ id: 456 }];
        expectedResult = service.addNBUserToCollectionIfMissing(nBUserCollection, nBUser);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nBUser);
      });

      it('should add only unique NBUser to an array', () => {
        const nBUserArray: INBUser[] = [{ id: 123 }, { id: 456 }, { id: 53764 }];
        const nBUserCollection: INBUser[] = [{ id: 123 }];
        expectedResult = service.addNBUserToCollectionIfMissing(nBUserCollection, ...nBUserArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nBUser: INBUser = { id: 123 };
        const nBUser2: INBUser = { id: 456 };
        expectedResult = service.addNBUserToCollectionIfMissing([], nBUser, nBUser2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nBUser);
        expect(expectedResult).toContain(nBUser2);
      });

      it('should accept null and undefined values', () => {
        const nBUser: INBUser = { id: 123 };
        expectedResult = service.addNBUserToCollectionIfMissing([], null, nBUser, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nBUser);
      });

      it('should return initial array if no NBUser is added', () => {
        const nBUserCollection: INBUser[] = [{ id: 123 }];
        expectedResult = service.addNBUserToCollectionIfMissing(nBUserCollection, undefined, null);
        expect(expectedResult).toEqual(nBUserCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
