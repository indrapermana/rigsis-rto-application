/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RtoAppTestModule } from '../../../test.module';
import { RecordItemDeleteDialogComponent } from 'app/entities/record-item/record-item-delete-dialog.component';
import { RecordItemService } from 'app/entities/record-item/record-item.service';

describe('Component Tests', () => {
    describe('RecordItem Management Delete Component', () => {
        let comp: RecordItemDeleteDialogComponent;
        let fixture: ComponentFixture<RecordItemDeleteDialogComponent>;
        let service: RecordItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [RecordItemDeleteDialogComponent]
            })
                .overrideTemplate(RecordItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecordItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecordItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
