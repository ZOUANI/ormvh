import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
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

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(natureCourrier: INatureCourrier): INatureCourrier {
    const copy: INatureCourrier = Object.assign({}, natureCourrier, {
      createdAt: natureCourrier.createdAt && natureCourrier.createdAt.isValid() ? natureCourrier.createdAt.toJSON() : undefined,
      updatedAt: natureCourrier.updatedAt && natureCourrier.updatedAt.isValid() ? natureCourrier.updatedAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((natureCourrier: INatureCourrier) => {
        natureCourrier.createdAt = natureCourrier.createdAt ? moment(natureCourrier.createdAt) : undefined;
        natureCourrier.updatedAt = natureCourrier.updatedAt ? moment(natureCourrier.updatedAt) : undefined;
      });
    }
    return res;
  }
}
