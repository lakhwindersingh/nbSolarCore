import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INBUser, getNBUserIdentifier } from '../nb-user.model';

export type EntityResponseType = HttpResponse<INBUser>;
export type EntityArrayResponseType = HttpResponse<INBUser[]>;

@Injectable({ providedIn: 'root' })
export class NBUserService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nb-users');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nBUser: INBUser): Observable<EntityResponseType> {
    return this.http.post<INBUser>(this.resourceUrl, nBUser, { observe: 'response' });
  }

  update(nBUser: INBUser): Observable<EntityResponseType> {
    return this.http.put<INBUser>(`${this.resourceUrl}/${getNBUserIdentifier(nBUser) as number}`, nBUser, { observe: 'response' });
  }

  partialUpdate(nBUser: INBUser): Observable<EntityResponseType> {
    return this.http.patch<INBUser>(`${this.resourceUrl}/${getNBUserIdentifier(nBUser) as number}`, nBUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INBUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INBUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNBUserToCollectionIfMissing(nBUserCollection: INBUser[], ...nBUsersToCheck: (INBUser | null | undefined)[]): INBUser[] {
    const nBUsers: INBUser[] = nBUsersToCheck.filter(isPresent);
    if (nBUsers.length > 0) {
      const nBUserCollectionIdentifiers = nBUserCollection.map(nBUserItem => getNBUserIdentifier(nBUserItem)!);
      const nBUsersToAdd = nBUsers.filter(nBUserItem => {
        const nBUserIdentifier = getNBUserIdentifier(nBUserItem);
        if (nBUserIdentifier == null || nBUserCollectionIdentifiers.includes(nBUserIdentifier)) {
          return false;
        }
        nBUserCollectionIdentifiers.push(nBUserIdentifier);
        return true;
      });
      return [...nBUsersToAdd, ...nBUserCollection];
    }
    return nBUserCollection;
  }
}
