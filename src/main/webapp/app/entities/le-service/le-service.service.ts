import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILeService } from 'app/shared/model/le-service.model';

type EntityResponseType = HttpResponse<ILeService>;
type EntityArrayResponseType = HttpResponse<ILeService[]>;

@Injectable({ providedIn: 'root' })
export class LeServiceService {
  public resourceUrl = SERVER_API_URL + 'api/le-services';

  constructor(protected http: HttpClient) {}

  create(leService: ILeService): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leService);
    return this.http
      .post<ILeService>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(leService: ILeService): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leService);
    return this.http
      .put<ILeService>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILeService>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILeService[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(leService: ILeService): ILeService {
    const copy: ILeService = Object.assign({}, leService, {
      createdAt: leService.createdAt != null && leService.createdAt.isValid() ? leService.createdAt.toJSON() : null,
      updatedAt: leService.updatedAt != null && leService.updatedAt.isValid() ? leService.updatedAt.toJSON() : null
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
      res.body.forEach((leService: ILeService) => {
        leService.createdAt = leService.createdAt != null ? moment(leService.createdAt) : null;
        leService.updatedAt = leService.updatedAt != null ? moment(leService.updatedAt) : null;
      });
    }
    return res;
  }
}
