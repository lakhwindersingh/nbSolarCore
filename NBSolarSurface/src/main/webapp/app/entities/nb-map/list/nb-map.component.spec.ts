import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NBMapService } from '../service/nb-map.service';

import { NBMapComponent } from './nb-map.component';

describe('NBMap Management Component', () => {
  let comp: NBMapComponent;
  let fixture: ComponentFixture<NBMapComponent>;
  let service: NBMapService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NBMapComponent],
    })
      .overrideTemplate(NBMapComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NBMapComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NBMapService);

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
    expect(comp.nBMaps?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
