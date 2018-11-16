import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRecordType } from 'app/shared/model/record-type.model';
import { Principal } from 'app/core';
import { RecordTypeService } from './record-type.service';

@Component({
    selector: 'jhi-record-type',
    templateUrl: './record-type.component.html'
})
export class RecordTypeComponent implements OnInit, OnDestroy {
    recordTypes: IRecordType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private recordTypeService: RecordTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.recordTypeService.query().subscribe(
            (res: HttpResponse<IRecordType[]>) => {
                this.recordTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRecordTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRecordType) {
        return item.id;
    }

    registerChangeInRecordTypes() {
        this.eventSubscriber = this.eventManager.subscribe('recordTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
