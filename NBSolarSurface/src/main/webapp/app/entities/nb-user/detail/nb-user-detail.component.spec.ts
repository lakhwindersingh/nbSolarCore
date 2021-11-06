import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBUserDetailComponent } from './nb-user-detail.component';

describe('NBUser Management Detail Component', () => {
  let comp: NBUserDetailComponent;
  let fixture: ComponentFixture<NBUserDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NBUserDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ nBUser: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NBUserDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NBUserDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load nBUser on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.nBUser).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
