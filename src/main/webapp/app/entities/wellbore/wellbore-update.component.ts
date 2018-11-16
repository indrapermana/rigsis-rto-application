import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IWellbore } from 'app/shared/model/wellbore.model';
import { WellboreService } from './wellbore.service';
import { IWell } from 'app/shared/model/well.model';
import { WellService } from 'app/entities/well';

@Component({
    selector: 'jhi-wellbore-update',
    templateUrl: './wellbore-update.component.html'
})
export class WellboreUpdateComponent implements OnInit {
    wellbore: IWellbore;
    isSaving: boolean;

    wells: IWell[];

    wellbores: IWellbore[];
    kickOffDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private wellboreService: WellboreService,
        private wellService: WellService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ wellbore }) => {
            this.wellbore = wellbore;
            this.kickOffDate = this.wellbore.kickOffDate != null ? this.wellbore.kickOffDate.format(DATE_TIME_FORMAT) : null;
        });
        this.wellService.query().subscribe(
            (res: HttpResponse<IWell[]>) => {
                this.wells = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.wellboreService.query().subscribe(
            (res: HttpResponse<IWellbore[]>) => {
                this.wellbores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.wellbore.kickOffDate = this.kickOffDate != null ? moment(this.kickOffDate, DATE_TIME_FORMAT) : null;
        if (this.wellbore.id !== undefined) {
            this.subscribeToSaveResponse(this.wellboreService.update(this.wellbore));
        } else {
            this.subscribeToSaveResponse(this.wellboreService.create(this.wellbore));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWellbore>>) {
        result.subscribe((res: HttpResponse<IWellbore>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackWellById(index: number, item: IWell) {
        return item.id;
    }

    trackWellboreById(index: number, item: IWellbore) {
        return item.id;
    }
}
