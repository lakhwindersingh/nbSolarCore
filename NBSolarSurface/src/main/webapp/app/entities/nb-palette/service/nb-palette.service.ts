import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INBPalette, getNBPaletteIdentifier } from '../nb-palette.model';

export type EntityResponseType = HttpResponse<INBPalette>;
export type EntityArrayResponseType = HttpResponse<INBPalette[]>;

@Injectable({ providedIn: 'root' })
export class NBPaletteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nb-palettes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nBPalette: INBPalette): Observable<EntityResponseType> {
    return this.http.post<INBPalette>(this.resourceUrl, nBPalette, { observe: 'response' });
  }

  update(nBPalette: INBPalette): Observable<EntityResponseType> {
    return this.http.put<INBPalette>(`${this.resourceUrl}/${getNBPaletteIdentifier(nBPalette) as number}`, nBPalette, {
      observe: 'response',
    });
  }

  partialUpdate(nBPalette: INBPalette): Observable<EntityResponseType> {
    return this.http.patch<INBPalette>(`${this.resourceUrl}/${getNBPaletteIdentifier(nBPalette) as number}`, nBPalette, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INBPalette>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INBPalette[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNBPaletteToCollectionIfMissing(
    nBPaletteCollection: INBPalette[],
    ...nBPalettesToCheck: (INBPalette | null | undefined)[]
  ): INBPalette[] {
    const nBPalettes: INBPalette[] = nBPalettesToCheck.filter(isPresent);
    if (nBPalettes.length > 0) {
      const nBPaletteCollectionIdentifiers = nBPaletteCollection.map(nBPaletteItem => getNBPaletteIdentifier(nBPaletteItem)!);
      const nBPalettesToAdd = nBPalettes.filter(nBPaletteItem => {
        const nBPaletteIdentifier = getNBPaletteIdentifier(nBPaletteItem);
        if (nBPaletteIdentifier == null || nBPaletteCollectionIdentifiers.includes(nBPaletteIdentifier)) {
          return false;
        }
        nBPaletteCollectionIdentifiers.push(nBPaletteIdentifier);
        return true;
      });
      return [...nBPalettesToAdd, ...nBPaletteCollection];
    }
    return nBPaletteCollection;
  }
}
