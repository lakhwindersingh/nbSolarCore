import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBMapDetailComponent } from './nb-map-detail.component';

describe('NBMap Management Detail Component', () => {
  let comp: NBMapDetailComponent;
  let fixture: ComponentFixture<NBMapDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NBMapDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ nBMap: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NBMapDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NBMapDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load nBMap on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.nBMap).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
