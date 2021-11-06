import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBPaletteDetailComponent } from './nb-palette-detail.component';

describe('NBPalette Management Detail Component', () => {
  let comp: NBPaletteDetailComponent;
  let fixture: ComponentFixture<NBPaletteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NBPaletteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ nBPalette: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NBPaletteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NBPaletteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load nBPalette on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.nBPalette).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
