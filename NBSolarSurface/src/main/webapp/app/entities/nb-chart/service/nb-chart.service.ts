import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INBChart, getNBChartIdentifier } from '../nb-chart.model';

export type EntityResponseType = HttpResponse<INBChart>;
export type EntityArrayResponseType = HttpResponse<INBChart[]>;

@Injectable({ providedIn: 'root' })
export class NBChartService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nb-charts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nBChart: INBChart): Observable<EntityResponseType> {
    return this.http.post<INBChart>(this.resourceUrl, nBChart, { observe: 'response' });
  }

  update(nBChart: INBChart): Observable<EntityResponseType> {
    return this.http.put<INBChart>(`${this.resourceUrl}/${getNBChartIdentifier(nBChart) as number}`, nBChart, { observe: 'response' });
  }

  partialUpdate(nBChart: INBChart): Observable<EntityResponseType> {
    return this.http.patch<INBChart>(`${this.resourceUrl}/${getNBChartIdentifier(nBChart) as number}`, nBChart, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INBChart>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INBChart[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNBChartToCollectionIfMissing(nBChartCollection: INBChart[], ...nBChartsToCheck: (INBChart | null | undefined)[]): INBChart[] {
    const nBCharts: INBChart[] = nBChartsToCheck.filter(isPresent);
    if (nBCharts.length > 0) {
      const nBChartCollectionIdentifiers = nBChartCollection.map(nBChartItem => getNBChartIdentifier(nBChartItem)!);
      const nBChartsToAdd = nBCharts.filter(nBChartItem => {
        const nBChartIdentifier = getNBChartIdentifier(nBChartItem);
        if (nBChartIdentifier == null || nBChartCollectionIdentifiers.includes(nBChartIdentifier)) {
          return false;
        }
        nBChartCollectionIdentifiers.push(nBChartIdentifier);
        return true;
      });
      return [...nBChartsToAdd, ...nBChartCollection];
    }
    return nBChartCollection;
  }
}
