jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NBPaletteService } from '../service/nb-palette.service';
import { INBPalette, NBPalette } from '../nb-palette.model';

import { NBPaletteUpdateComponent } from './nb-palette-update.component';

describe('NBPalette Management Update Component', () => {
  let comp: NBPaletteUpdateComponent;
  let fixture: ComponentFixture<NBPaletteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nBPaletteService: NBPaletteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NBPaletteUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NBPaletteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NBPaletteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nBPaletteService = TestBed.inject(NBPaletteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const nBPalette: INBPalette = { id: 456 };

      activatedRoute.data = of({ nBPalette });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(nBPalette));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBPalette>>();
      const nBPalette = { id: 123 };
      jest.spyOn(nBPaletteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBPalette });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nBPalette }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(nBPaletteService.update).toHaveBeenCalledWith(nBPalette);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBPalette>>();
      const nBPalette = new NBPalette();
      jest.spyOn(nBPaletteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBPalette });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nBPalette }));
      saveSubject.complete();

      // THEN
      expect(nBPaletteService.create).toHaveBeenCalledWith(nBPalette);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBPalette>>();
      const nBPalette = { id: 123 };
      jest.spyOn(nBPaletteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBPalette });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nBPaletteService.update).toHaveBeenCalledWith(nBPalette);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
