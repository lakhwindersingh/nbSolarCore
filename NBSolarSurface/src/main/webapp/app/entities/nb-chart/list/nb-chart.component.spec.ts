import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NBChartService } from '../service/nb-chart.service';

import { NBChartComponent } from './nb-chart.component';

describe('NBChart Management Component', () => {
  let comp: NBChartComponent;
  let fixture: ComponentFixture<NBChartComponent>;
  let service: NBChartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NBChartComponent],
    })
      .overrideTemplate(NBChartComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NBChartComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NBChartService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.nBCharts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
