import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NBUserService } from '../service/nb-user.service';

import { NBUserComponent } from './nb-user.component';

describe('NBUser Management Component', () => {
  let comp: NBUserComponent;
  let fixture: ComponentFixture<NBUserComponent>;
  let service: NBUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NBUserComponent],
    })
      .overrideTemplate(NBUserComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NBUserComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NBUserService);

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
    expect(comp.nBUsers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
