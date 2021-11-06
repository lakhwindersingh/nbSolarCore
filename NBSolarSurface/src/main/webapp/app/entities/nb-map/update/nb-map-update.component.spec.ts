jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NBMapService } from '../service/nb-map.service';
import { INBMap, NBMap } from '../nb-map.model';

import { NBMapUpdateComponent } from './nb-map-update.component';

describe('NBMap Management Update Component', () => {
  let comp: NBMapUpdateComponent;
  let fixture: ComponentFixture<NBMapUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nBMapService: NBMapService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NBMapUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NBMapUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NBMapUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nBMapService = TestBed.inject(NBMapService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const nBMap: INBMap = { id: 456 };

      activatedRoute.data = of({ nBMap });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(nBMap));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBMap>>();
      const nBMap = { id: 123 };
      jest.spyOn(nBMapService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBMap });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nBMap }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(nBMapService.update).toHaveBeenCalledWith(nBMap);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBMap>>();
      const nBMap = new NBMap();
      jest.spyOn(nBMapService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBMap });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nBMap }));
      saveSubject.complete();

      // THEN
      expect(nBMapService.create).toHaveBeenCalledWith(nBMap);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBMap>>();
      const nBMap = { id: 123 };
      jest.spyOn(nBMapService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBMap });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nBMapService.update).toHaveBeenCalledWith(nBMap);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
