import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBChartDetailComponent } from './nb-chart-detail.component';

describe('NBChart Management Detail Component', () => {
  let comp: NBChartDetailComponent;
  let fixture: ComponentFixture<NBChartDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NBChartDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ nBChart: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NBChartDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NBChartDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load nBChart on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.nBChart).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
