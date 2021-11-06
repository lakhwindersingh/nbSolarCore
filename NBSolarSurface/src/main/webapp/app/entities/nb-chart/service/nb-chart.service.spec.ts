import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INBChart, NBChart } from '../nb-chart.model';

import { NBChartService } from './nb-chart.service';

describe('NBChart Service', () => {
  let service: NBChartService;
  let httpMock: HttpTestingController;
  let elemDefault: INBChart;
  let expectedResult: INBChart | INBChart[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NBChartService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nbChartID: 'AAAAAAA',
      nbChartTitle: 'AAAAAAA',
      nbChartType: 'AAAAAAA',
      nbChartParams: 'AAAAAAA',
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

    it('should create a NBChart', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new NBChart()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NBChart', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nbChartID: 'BBBBBB',
          nbChartTitle: 'BBBBBB',
          nbChartType: 'BBBBBB',
          nbChartParams: 'BBBBBB',
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

    it('should partial update a NBChart', () => {
      const patchObject = Object.assign(
        {
          nbChartTitle: 'BBBBBB',
          nbChartParams: 'BBBBBB',
          nbLastUpdated: 'BBBBBB',
        },
        new NBChart()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NBChart', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nbChartID: 'BBBBBB',
          nbChartTitle: 'BBBBBB',
          nbChartType: 'BBBBBB',
          nbChartParams: 'BBBBBB',
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

    it('should delete a NBChart', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNBChartToCollectionIfMissing', () => {
      it('should add a NBChart to an empty array', () => {
        const nBChart: INBChart = { id: 123 };
        expectedResult = service.addNBChartToCollectionIfMissing([], nBChart);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nBChart);
      });

      it('should not add a NBChart to an array that contains it', () => {
        const nBChart: INBChart = { id: 123 };
        const nBChartCollection: INBChart[] = [
          {
            ...nBChart,
          },
          { id: 456 },
        ];
        expectedResult = service.addNBChartToCollectionIfMissing(nBChartCollection, nBChart);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NBChart to an array that doesn't contain it", () => {
        const nBChart: INBChart = { id: 123 };
        const nBChartCollection: INBChart[] = [{ id: 456 }];
        expectedResult = service.addNBChartToCollectionIfMissing(nBChartCollection, nBChart);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nBChart);
      });

      it('should add only unique NBChart to an array', () => {
        const nBChartArray: INBChart[] = [{ id: 123 }, { id: 456 }, { id: 52800 }];
        const nBChartCollection: INBChart[] = [{ id: 123 }];
        expectedResult = service.addNBChartToCollectionIfMissing(nBChartCollection, ...nBChartArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nBChart: INBChart = { id: 123 };
        const nBChart2: INBChart = { id: 456 };
        expectedResult = service.addNBChartToCollectionIfMissing([], nBChart, nBChart2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nBChart);
        expect(expectedResult).toContain(nBChart2);
      });

      it('should accept null and undefined values', () => {
        const nBChart: INBChart = { id: 123 };
        expectedResult = service.addNBChartToCollectionIfMissing([], null, nBChart, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nBChart);
      });

      it('should return initial array if no NBChart is added', () => {
        const nBChartCollection: INBChart[] = [{ id: 123 }];
        expectedResult = service.addNBChartToCollectionIfMissing(nBChartCollection, undefined, null);
        expect(expectedResult).toEqual(nBChartCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
