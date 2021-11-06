jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NBUserService } from '../service/nb-user.service';
import { INBUser, NBUser } from '../nb-user.model';

import { NBUserUpdateComponent } from './nb-user-update.component';

describe('NBUser Management Update Component', () => {
  let comp: NBUserUpdateComponent;
  let fixture: ComponentFixture<NBUserUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nBUserService: NBUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NBUserUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NBUserUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NBUserUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nBUserService = TestBed.inject(NBUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const nBUser: INBUser = { id: 456 };

      activatedRoute.data = of({ nBUser });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(nBUser));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBUser>>();
      const nBUser = { id: 123 };
      jest.spyOn(nBUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nBUser }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(nBUserService.update).toHaveBeenCalledWith(nBUser);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBUser>>();
      const nBUser = new NBUser();
      jest.spyOn(nBUserService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nBUser }));
      saveSubject.complete();

      // THEN
      expect(nBUserService.create).toHaveBeenCalledWith(nBUser);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NBUser>>();
      const nBUser = { id: 123 };
      jest.spyOn(nBUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nBUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nBUserService.update).toHaveBeenCalledWith(nBUser);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
