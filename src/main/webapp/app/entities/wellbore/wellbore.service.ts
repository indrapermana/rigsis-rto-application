import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWellbore } from 'app/shared/model/wellbore.model';

type EntityResponseType = HttpResponse<IWellbore>;
type EntityArrayResponseType = HttpResponse<IWellbore[]>;

@Injectable({ providedIn: 'root' })
export class WellboreService {
    public resourceUrl = SERVER_API_URL + 'api/wellbores';

    constructor(private http: HttpClient) {}

    create(wellbore: IWellbore): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(wellbore);
        return this.http
            .post<IWellbore>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(wellbore: IWellbore): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(wellbore);
        return this.http
            .put<IWellbore>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IWellbore>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWellbore[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(wellbore: IWellbore): IWellbore {
        const copy: IWellbore = Object.assign({}, wellbore, {
            kickOffDate: wellbore.kickOffDate != null && wellbore.kickOffDate.isValid() ? wellbore.kickOffDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.kickOffDate = res.body.kickOffDate != null ? moment(res.body.kickOffDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((wellbore: IWellbore) => {
            wellbore.kickOffDate = wellbore.kickOffDate != null ? moment(wellbore.kickOffDate) : null;
        });
        return res;
    }
}
