jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NBChartService } from '../service/nb-chart.service';
import { INBChart, NBChart } from '../nb-chart.model';

import { NBChartUpdateComponent } from './nb-chart-update.component';

describe('NBChart Management Update Component', () => {
  let comp: NBChartUpdateComponent;
  let fixture: ComponentFixture<NBChartUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nBChartService: NBChartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NBChartUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NBChartUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NBChartUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nBChartService = TestBed.inject(NBChartService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const nBChart: INBChart = { id: 456 };

      activatedRoute.data = of({ nBChart });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(nBChart));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBChart>>();
      const nBChart = { id: 123 };
      jest.spyOn(nBChartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBChart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nBChart }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(nBChartService.update).toHaveBeenCalledWith(nBChart);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBChart>>();
      const nBChart = new NBChart();
      jest.spyOn(nBChartService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBChart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nBChart }));
      saveSubject.complete();

      // THEN
      expect(nBChartService.create).toHaveBeenCalledWith(nBChart);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBChart>>();
      const nBChart = { id: 123 };
      jest.spyOn(nBChartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBChart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nBChartService.update).toHaveBeenCalledWith(nBChart);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
