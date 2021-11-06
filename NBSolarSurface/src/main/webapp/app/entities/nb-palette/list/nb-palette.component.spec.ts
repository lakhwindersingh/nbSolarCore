import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NBPaletteService } from '../service/nb-palette.service';

import { NBPaletteComponent } from './nb-palette.component';

describe('NBPalette Management Component', () => {
  let comp: NBPaletteComponent;
  let fixture: ComponentFixture<NBPaletteComponent>;
  let service: NBPaletteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NBPaletteComponent],
    })
      .overrideTemplate(NBPaletteComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NBPaletteComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NBPaletteService);

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
    expect(comp.nBPalettes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
