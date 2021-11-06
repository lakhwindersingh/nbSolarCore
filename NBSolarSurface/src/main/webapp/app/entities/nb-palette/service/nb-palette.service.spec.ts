import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INBPalette, NBPalette } from '../nb-palette.model';

import { NBPaletteService } from './nb-palette.service';

describe('NBPalette Service', () => {
  let service: NBPaletteService;
  let httpMock: HttpTestingController;
  let elemDefault: INBPalette;
  let expectedResult: INBPalette | INBPalette[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NBPaletteService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nbPaletteID: 'AAAAAAA',
      nbPaletteTitle: 'AAAAAAA',
      nbPaletteType: 'AAAAAAA',
      nbPaletteColors: 'AAAAAAA',
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

    it('should create a NBPalette', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new NBPalette()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NBPalette', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nbPaletteID: 'BBBBBB',
          nbPaletteTitle: 'BBBBBB',
          nbPaletteType: 'BBBBBB',
          nbPaletteColors: 'BBBBBB',
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

    it('should partial update a NBPalette', () => {
      const patchObject = Object.assign(
        {
          nbPaletteTitle: 'BBBBBB',
          nbPaletteType: 'BBBBBB',
          nbLastUpdated: 'BBBBBB',
          nbLastUpdatedBy: 'BBBBBB',
        },
        new NBPalette()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NBPalette', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nbPaletteID: 'BBBBBB',
          nbPaletteTitle: 'BBBBBB',
          nbPaletteType: 'BBBBBB',
          nbPaletteColors: 'BBBBBB',
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

    it('should delete a NBPalette', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNBPaletteToCollectionIfMissing', () => {
      it('should add a NBPalette to an empty array', () => {
        const nBPalette: INBPalette = { id: 123 };
        expectedResult = service.addNBPaletteToCollectionIfMissing([], nBPalette);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nBPalette);
      });

      it('should not add a NBPalette to an array that contains it', () => {
        const nBPalette: INBPalette = { id: 123 };
        const nBPaletteCollection: INBPalette[] = [
          {
            ...nBPalette,
          },
          { id: 456 },
        ];
        expectedResult = service.addNBPaletteToCollectionIfMissing(nBPaletteCollection, nBPalette);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NBPalette to an array that doesn't contain it", () => {
        const nBPalette: INBPalette = { id: 123 };
        const nBPaletteCollection: INBPalette[] = [{ id: 456 }];
        expectedResult = service.addNBPaletteToCollectionIfMissing(nBPaletteCollection, nBPalette);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nBPalette);
      });

      it('should add only unique NBPalette to an array', () => {
        const nBPaletteArray: INBPalette[] = [{ id: 123 }, { id: 456 }, { id: 90985 }];
        const nBPaletteCollection: INBPalette[] = [{ id: 123 }];
        expectedResult = service.addNBPaletteToCollectionIfMissing(nBPaletteCollection, ...nBPaletteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nBPalette: INBPalette = { id: 123 };
        const nBPalette2: INBPalette = { id: 456 };
        expectedResult = service.addNBPaletteToCollectionIfMissing([], nBPalette, nBPalette2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nBPalette);
        expect(expectedResult).toContain(nBPalette2);
      });

      it('should accept null and undefined values', () => {
        const nBPalette: INBPalette = { id: 123 };
        expectedResult = service.addNBPaletteToCollectionIfMissing([], null, nBPalette, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nBPalette);
      });

      it('should return initial array if no NBPalette is added', () => {
        const nBPaletteCollection: INBPalette[] = [{ id: 123 }];
        expectedResult = service.addNBPaletteToCollectionIfMissing(nBPaletteCollection, undefined, null);
        expect(expectedResult).toEqual(nBPaletteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
