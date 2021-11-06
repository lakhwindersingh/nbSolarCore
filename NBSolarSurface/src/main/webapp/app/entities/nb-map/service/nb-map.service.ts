import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INBMap, getNBMapIdentifier } from '../nb-map.model';

export type EntityResponseType = HttpResponse<INBMap>;
export type EntityArrayResponseType = HttpResponse<INBMap[]>;

@Injectable({ providedIn: 'root' })
export class NBMapService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nb-maps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nBMap: INBMap): Observable<EntityResponseType> {
    return this.http.post<INBMap>(this.resourceUrl, nBMap, { observe: 'response' });
  }

  update(nBMap: INBMap): Observable<EntityResponseType> {
    return this.http.put<INBMap>(`${this.resourceUrl}/${getNBMapIdentifier(nBMap) as number}`, nBMap, { observe: 'response' });
  }

  partialUpdate(nBMap: INBMap): Observable<EntityResponseType> {
    return this.http.patch<INBMap>(`${this.resourceUrl}/${getNBMapIdentifier(nBMap) as number}`, nBMap, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INBMap>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INBMap[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNBMapToCollectionIfMissing(nBMapCollection: INBMap[], ...nBMapsToCheck: (INBMap | null | undefined)[]): INBMap[] {
    const nBMaps: INBMap[] = nBMapsToCheck.filter(isPresent);
    if (nBMaps.length > 0) {
      const nBMapCollectionIdentifiers = nBMapCollection.map(nBMapItem => getNBMapIdentifier(nBMapItem)!);
      const nBMapsToAdd = nBMaps.filter(nBMapItem => {
        const nBMapIdentifier = getNBMapIdentifier(nBMapItem);
        if (nBMapIdentifier == null || nBMapCollectionIdentifiers.includes(nBMapIdentifier)) {
          return false;
        }
        nBMapCollectionIdentifiers.push(nBMapIdentifier);
        return true;
      });
      return [...nBMapsToAdd, ...nBMapCollection];
    }
    return nBMapCollection;
  }
}
