import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INatureCourrier } from 'app/shared/model/nature-courrier.model';

type EntityResponseType = HttpResponse<INatureCourrier>;
type EntityArrayResponseType = HttpResponse<INatureCourrier[]>;

@Injectable({ providedIn: 'root' })
export class NatureCourrierService {
  public resourceUrl = SERVER_API_URL + 'api/nature-courriers';

  constructor(protected http: HttpClient) {}

  create(natureCourrier: INatureCourrier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureCourrier);
    return this.http
      .post<INatureCourrier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(natureCourrier: INatureCourrier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureCourrier);
    return this.http
      .put<INatureCourrier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INatureCourrier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INatureCourrier[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(natureCourrier: INatureCourrier): INatureCourrier {
    const copy: INatureCourrier = Object.assign({}, natureCourrier, {
      createdAt: natureCourrier.createdAt != null && natureCourrier.createdAt.isValid() ? natureCourrier.createdAt.toJSON() : null,
      updatedAt: natureCourrier.updatedAt != null && natureCourrier.updatedAt.isValid() ? natureCourrier.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((natureCourrier: INatureCourrier) => {
        natureCourrier.createdAt = natureCourrier.createdAt != null ? moment(natureCourrier.createdAt) : null;
        natureCourrier.updatedAt = natureCourrier.updatedAt != null ? moment(natureCourrier.updatedAt) : null;
      });
    }
    return res;
  }
}
